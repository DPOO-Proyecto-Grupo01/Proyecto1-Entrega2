package Perisistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;

import Actividades.*;
import Consola.ConsolaEstudiante;
import LearningPaths.LearningPath;

import org.json.JSONArray;
import org.json.JSONObject;

public class PersistenciaActividades implements IpersistenciaActividades {

    @Override
    public ArrayList<Actividad> cargarActividades(String archivo) throws Exception {
       
        String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
        JSONArray jActividades = new JSONArray(jsonCompleto);
        ArrayList<Actividad> lista = new ArrayList<>();
        for (int i = 0; i < jActividades.length(); i++) {
            JSONObject actividad = jActividades.getJSONObject(i);
            String actividadID = actividad.get("actividadID").toString();
            String descripcion = actividad.get("descripcion").toString();
            String objetivo = actividad.get("objetivo").toString();
            int nivelDificultad = actividad.getInt("nivelDificultad");
            int duracionEsperada = actividad.getInt("duracionEsperada");
            boolean esObligatoria = actividad.getBoolean("esObligatoria");
            // Convertir la fecha de string a date, si es 
 
            String fechaLimite2 = null;
            if (actividad.has("fechaLimite2") && actividad.get("fechaLimite2") instanceof Number) {
                fechaLimite2 = String.valueOf(actividad.getLong("fechaLimite2"));
            } else if (actividad.has("fechaLimite") && actividad.get("fechaLimite") instanceof Number) {
                fechaLimite2 = String.valueOf(actividad.getLong("fechaLimite"));
            } else if (actividad.has("fechaLimite2")) {
                fechaLimite2 = actividad.getString("fechaLimite2");
            } else if (actividad.has("fechaLimite")) {
                fechaLimite2 = actividad.getString("fechaLimite");
            }
			Date fechaLimite = null;
			try {
				
				long fechaLimiteMillis = Long.parseLong(fechaLimite2);
				fechaLimite = new Date(fechaLimiteMillis);
			} catch (NumberFormatException e) {
				
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
				LocalDateTime fechaLimiteD = LocalDateTime.parse(fechaLimite2, formatter); 
				fechaLimite = Date.from(fechaLimiteD.atZone(ZoneId.systemDefault()).toInstant());
			}
			

            String resenas = actividad.get("resenas").toString();
            double calificacion = actividad.getDouble("calificacion");
            int resultado = actividad.getInt("resultado");
            JSONArray actividadesPreviasArray = actividad.getJSONArray("actividadesPrevias");
            List<String> actividadesPrevias = new ArrayList<>();
            for (int j = 0; j < actividadesPreviasArray.length(); j++) {
                actividadesPrevias.add(actividadesPreviasArray.getString(j));
            }
            JSONArray actividadesSeguimientoArray = actividad.getJSONArray("actividadesSeguimiento");
            List<String> actividadesSeguimiento = new ArrayList<>();
            for (int j = 0; j < actividadesSeguimientoArray.length(); j++) {
                actividadesSeguimiento.add(actividadesSeguimientoArray.getString(j));
            }

            String tipoActividad = actividad.get("tipoActividad").toString();
            if (tipoActividad.equalsIgnoreCase("quiz")) {
                double calificacionMinima = actividad.getDouble("calificacionMinima");
                List<Pregunta> preguntas = getPreguntaList(actividad);
                String respuestaCorrecta = actividad.getString("respuestaCorrecta");
                Quiz quiz = new Quiz(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevias, actividadesSeguimiento, preguntas, calificacionMinima, respuestaCorrecta);
                lista.add(quiz);
            }else if (tipoActividad.equalsIgnoreCase("examen")) {
                double calificacionQuiz = actividad.getDouble("calificacionMinima");
                List<Pregunta> preguntas = getPreguntaList(actividad);
                Examen ex = new Examen(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevias, actividadesSeguimiento, preguntas, calificacionQuiz);
                lista.add(ex);
            } else if (tipoActividad.equalsIgnoreCase("tarea")) {
                String estado = actividad.get("estado").toString();
                List<Pregunta> preguntas = getPreguntaList(actividad);
                String InstruccionesArray = actividad.get("instrucciones").toString();
                Tarea tarea = new Tarea(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado,
                        actividadesPrevias, actividadesSeguimiento, preguntas, InstruccionesArray, estado);
                lista.add(tarea);
            } else if (tipoActividad.equalsIgnoreCase("Encuesta")) {
                JSONArray preguntasArray1 = actividad.getJSONArray("preguntasAbiertas");
                List<String> preguntas1 = new ArrayList<>();
                for (int j = 0; j < preguntasArray1.length(); j++) {
                    preguntas1.add(preguntasArray1.getString(j));
                }
                Encuesta e = new Encuesta(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevias, actividadesSeguimiento, preguntas1);
                lista.add(e);
            } else if (tipoActividad.equalsIgnoreCase("RecursoEducativo")){
            	String tipoRecurso = null;
            	if (actividad.has("tipoRecurso")) {
            	    tipoRecurso = actividad.getString("tipoRecurso");
            	} else if (actividad.has("tiporecurso")) {
            	    tipoRecurso = actividad.getString("tiporecurso");
            	}
                String linkRecurso = actividad.get("linkRecurso").toString();
                RecursoEducativo r = new RecursoEducativo(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria,
                        fechaLimite, resenas, calificacion, resultado, actividadesPrevias, actividadesSeguimiento, tipoRecurso, linkRecurso);
                lista.add(r);
            }
        }
        return lista;
    }

	public void actualizarActividadesEstudiante(Actividad act) throws Exception {
		String archivo = ConsolaEstudiante.getActividadesFile();
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(archivo)));
            // Parse the content to a JSON array
            if (content.trim().isEmpty())
                content = "[]";
            JSONArray jsonArray = new JSONArray(content);

            JSONObject object = act.convertToJSONObject();
            //TODO Lidiar con ACTUALIZAR una actividad que ya fue guardada
            jsonArray.put(object);
            Files.write(Paths.get(archivo), jsonArray.toString().getBytes());
        } catch (IOException e) {
        	System.out.println("Error al guardar actividad: " + act);
            throw new RuntimeException(e);
            
        } }

    /**
     * Coge una lista de preguntas en JSON y la vuelve un List<Preguntas>
     */
    private static List<Pregunta> getPreguntaList(JSONObject actividad) {
        List<Pregunta> preguntas = new ArrayList<>();
        JSONArray preguntasArray = actividad.getJSONArray("preguntas");
        for (int j = 0; j < preguntasArray.length(); j++) {
            JSONObject pregunta = preguntasArray.getJSONObject(j);
            String preguntaID = pregunta.get("pregunta").toString();
            JSONArray opcionesArray = pregunta.getJSONArray("opciones");
            List<String> opciones = new ArrayList<>();
            for (int k = 0; k < opcionesArray.length(); k++) {
                opciones.add(opcionesArray.getString(k));
            }
            preguntas.add(new Pregunta(preguntaID, opciones));
        }
        return preguntas;
    }
    @Override
    public void salvarActividad(String archivo, Actividad actividad){
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(archivo)));
            // Parse the content to a JSON array
            if (content.trim().isEmpty())
                content = "[]";
            JSONArray jsonArray = new JSONArray(content);

            JSONObject object = actividad.convertToJSONObject();
            //TODO Lidiar con ACTUALIZAR una actividad que ya fue guardada
            jsonArray.put(object);
            Files.write(Paths.get(archivo), jsonArray.toString().getBytes());
        } catch (IOException e) {
        	System.out.println("Error al guardar actividad: " + actividad);
            throw new RuntimeException(e);
        }
    }

    public void VIEJOsalvarActividad(String archivo, String actividadID, String descripcion, String objetivo, int nivelDificultad,
                                int duracionEsperada, boolean esObligatoria, Date fechaLimite2, String resenas, double calificacion,
                                int resultado, List<String> actividadesPrevias, List<String> actividadesSeguimiento, String tipoActividad,
                                Map<String, List<String>> preguntasCerradas, List<String> preguntasAbiertas, String instrucciones,
                                String estado, String tipoRecurso, double calificacionMinima) {


        try {
            String content = new String(Files.readAllBytes(Paths.get(archivo)));
            // Parse the content to a JSON array
            JSONArray jsonArray = new JSONArray(content);
            // Create a new JSON object for the new student
            JSONObject newObject = new JSONObject();
            newObject.put("actividadID", actividadID);
            newObject.put("descripcion", descripcion);
            newObject.put("objetivo", objetivo);
            newObject.put("nivelDificultad", nivelDificultad);
            newObject.put("duracionEsperada", duracionEsperada);
            newObject.put("esObligatoria", esObligatoria);
            newObject.put("fechaLimite", fechaLimite2.getTime());
            newObject.put("resenas", resenas);
            newObject.put("calificacion", calificacion);
            newObject.put("resultado", resultado);
            newObject.put("actividadesPrevias", actividadesPrevias);
            newObject.put("actividadesSeguimiento", actividadesSeguimiento);
            newObject.put("tipoActividad", tipoActividad);
            if (tipoActividad.equals("Quiz") || tipoActividad.equals("Examen")) {
                JSONArray preguntasArray = new JSONArray();
                for (String preguntaID : preguntasCerradas.keySet()) {
                    JSONObject pregunta = new JSONObject();
                    pregunta.put("preguntaID", preguntaID);
                    JSONArray opcionesArray = new JSONArray();
                    for (String opcion : preguntasCerradas.get(preguntaID)) {
                        opcionesArray.put(opcion);
                    }
                    pregunta.put("opciones", opcionesArray);
                    preguntasArray.put(pregunta);
                }
                newObject.put("preguntas", preguntasArray);
                newObject.put("calificacionMinima", calificacionMinima);
            } else if (tipoActividad.equals("Encuesta")) {
                JSONArray preguntasAbiertas1 = new JSONArray();
                for (int i = 0; i < preguntasAbiertas.size(); i++) {
                    preguntasAbiertas1.put(preguntasAbiertas.get(i));
                }
            } else if (tipoActividad.equals("Tarea")) {
                JSONArray preguntasArray = new JSONArray();
                for (String preguntaID : preguntasCerradas.keySet()) {
                    JSONObject pregunta = new JSONObject();
                    pregunta.put("preguntaID", preguntaID);
                    JSONArray opcionesArray = new JSONArray();
                    for (String opcion : preguntasCerradas.get(preguntaID)) {
                        opcionesArray.put(opcion);
                    }
                    pregunta.put("opciones", opcionesArray);
                    preguntasArray.put(pregunta);
                }
                newObject.put("Instrucciones", instrucciones);
                newObject.put("estado", estado);
            } else if (tipoActividad.equals("Recurso Educativo")) {
                newObject.put("tipoRecurso", tipoRecurso);
            }

            jsonArray.put(newObject);

            Files.write(Paths.get(archivo), jsonArray.toString().getBytes());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }


}
