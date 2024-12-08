package Perisistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Consola.ConsolaEstudiante;
import Consola.ConsolaProfesor;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;

public class PersistenciaFeedback {

    public List<Feedback> cargarFeedback(String archivo) throws Exception {
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        JSONArray feedbacks = new JSONArray(jsonCompleto);
        ArrayList<Feedback> lista = new ArrayList<>();

        for (int i = 0; i < feedbacks.length(); i++) {
            JSONObject feedbackJSON = feedbacks.getJSONObject(i);
            String feedbackID = feedbackJSON.getString("feedbackID");
            String comentario = feedbackJSON.getString("comentario");
            int calificacion = feedbackJSON.getInt("calificacion");
            String estudianteID = feedbackJSON.getString("estudianteID");
            String learningPathID = feedbackJSON.getString("learningPathID");

            Feedback nuevoFeedback = new Feedback(feedbackID, comentario, calificacion, estudianteID, learningPathID);
            lista.add(nuevoFeedback);
        }
        return lista;
    }

    public void actualizarFeedback(Feedback feedback) throws Exception {
        List<Feedback> listaFeedbacks = cargarFeedback(ConsolaEstudiante.getFeedbackFile());
        boolean encontrado = false;
        for (int i = 0; i < listaFeedbacks.size(); i++) {
            if (listaFeedbacks.get(i).getFeedbackID().equals(feedback.getFeedbackID())) {
                listaFeedbacks.set(i, feedback);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            listaFeedbacks.add(feedback);
        }
        salvarListaFeedback(listaFeedbacks);
    }

    public void salvarListaFeedback(List<Feedback> listaFeedbacks) throws Exception {
        JSONArray jsonArray = new JSONArray();
        for (Feedback feedback : listaFeedbacks) {
            jsonArray.put(feedback.toJSON());
        }
        try {
            Files.write(Paths.get(ConsolaEstudiante.getFeedbackFile()), jsonArray.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	
}
