package Perisistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import LearningPaths.LearningPath;

public class PersistenciaLearningPaths implements IpersistenciaLearningPaths {
	
	public List<LearningPath> cargarLearningPath(String archivo) throws Exception {

	   

	    String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
	    JSONArray learningPaths = new JSONArray(jsonCompleto);
	    ArrayList<LearningPath> lista = new ArrayList<LearningPath>();

	    for (int i = 0; i < learningPaths.length(); i++) {
	        JSONObject learningPath = learningPaths.getJSONObject(i);
	        String LearningPathID = learningPath.get("LearningPathID").toString();
	        String titulo = learningPath.get("titulo").toString();
	        String descripcion = learningPath.get("descripcion").toString();
	        String objetivos = learningPath.get("objetivos").toString();
	        int nivelDificultad = learningPath.getInt("nivelDificultad");
	        int duracion = learningPath.getInt("duracion");
	        String profesorID = learningPath.get("profesorID").toString();

	        // Convertir JSONArray de actividadesID a List<String>
	        JSONArray actividadesArray = learningPath.getJSONArray("actividadesID");
	        List<String> actividadesID = new ArrayList<>();
	        for (int j = 0; j < actividadesArray.length(); j++) {
	            actividadesID.add(actividadesArray.getString(j));
	        }

	        // Convertir JSONArray de intereses a List<String>
	        JSONArray interesesArray = learningPath.getJSONArray("intereses");
	        List<String> intereses = new ArrayList<>();
	        for (int j = 0; j < interesesArray.length(); j++) {
	            intereses.add(interesesArray.getString(j));
	        }

	        // Crear el objeto LearningPath
	        LearningPath nuevoLearningPath = new LearningPath(LearningPathID, titulo, descripcion,
	                objetivos, nivelDificultad, duracion, profesorID, actividadesID, intereses);

	        lista.add(nuevoLearningPath);
	    }
	    return lista;
	}


	public void salvarLearningPath(String archivo, String LearningPathID, String titulo, String descripcion, String objetivos,
			int nivelDificultad, int duracion, String profesorID, List<String> actividadesID, List<String> intereses ) {
		
		String content;
		try {
			content = new String(Files.readAllBytes(Paths.get(archivo)));
			JSONArray jsonArray = new JSONArray(content);
	        JSONObject newObject = new JSONObject();
	        newObject.put("LearningPathID", LearningPathID);
	        newObject.put("titulo", titulo);
	        newObject.put("descripcion", descripcion);
	        newObject.put("objetivos", objetivos);
	        newObject.put("nivelDificultad", nivelDificultad);
	        newObject.put("duracion", duracion);
	        newObject.put("profesorID", profesorID);
	        newObject.put("actividadesID", actividadesID);
	        newObject.put("intereses", intereses);
	        jsonArray.put(newObject);
	        Files.write(Paths.get(archivo), jsonArray.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
        
	}

}
