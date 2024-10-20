package Perisistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONObject;

import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;

public class PersistenciaActividades implements IpersistenciaActividades {
	
	@Override
	public ArrayList<Actividad> cargarActividad(String archivo ) throws IOException {
		System.out.println("Cargando Actividades");
		String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
		JSONArray actividades = new JSONArray(jsonCompleto);
		ArrayList<Actividad> quizes= new ArrayList<Actividad>();
		ArrayList<Actividad> tareas= new ArrayList<Actividad>();
		ArrayList<Actividad> examenes= new ArrayList<Actividad>();
		ArrayList<Actividad> encuestas= new ArrayList<Actividad>();
		ArrayList<Actividad> recursosEducativos= new ArrayList<Actividad>();
		
		
		
		for (int i = 0; i < actividades.length(); i++) {
           JSONObject actividad = actividades.getJSONObject(i);
           String actividadID = actividad.get("actividadID").toString();
           String descripcion = actividad.get("descripcion").toString();
           String objetivo = actividad.get("objetivo").toString();
           int nivelDificultad = actividad.getInt("nivelDificultad");
           int duracionEsperada = actividad.getInt("duracionEsperada");
           boolean esObligatoria = actividad.getBoolean("esObligatoria");
           // Convertir la fecha de string a date
           Date fechaLimite = new Date(actividad.getLong("fechaLimite"));
           
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
			
			if (tipoActividad.equals("Quiz") || tipoActividad.equals("Examen") || tipoActividad.equals("Tarea")) {
			    JSONArray preguntasArray = actividad.getJSONArray("preguntas");
			    Map<String, List<String>> preguntas = new HashMap<>();
				    for (int j = 0; j < preguntasArray.length(); j++) {
                    JSONObject pregunta = preguntasArray.getJSONObject(j);
                    String preguntaID = pregunta.get("preguntaID").toString();
                    JSONArray opcionesArray = pregunta.getJSONArray("opciones");
                    List<String> opciones = new ArrayList<>();
                    for (int k = 0; k < opcionesArray.length(); k++) {
                        opciones.add(opcionesArray.getString(k));
                    }
                    preguntas.put(preguntaID, opciones);
                }
				if (tipoActividad.equals("Quiz")) {
					double calificacionQuiz = actividad.getDouble("calificacionQuiz");
					Quiz quiz = new Quiz(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevias, actividadesSeguimiento, preguntas, calificacionQuiz);
					quizes.add(quiz);
                } else if (tipoActividad.equals("Examen")) {
                	double calificacionExamen = actividad.getDouble("calificacionExamen");
                	Examen examen = new Examen(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado,actividadesPrevias, actividadesSeguimiento, preguntas, calificacionExamen );
                    examenes.add(examen);
                } else if (tipoActividad.equals("Tarea")) {
                	String InstruccionesArray = actividad.get("Instrucciones").toString();
                	Tarea tarea = new Tarea(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado,actividadesPrevias, actividadesSeguimiento, preguntas, InstruccionesArray);
                    tareas.add(tarea);
				
				}
			}
			else if (tipoActividad.equals("Encuesta")) {
				JSONArray preguntasArray1 = actividad.getJSONArray("preguntas");
				List<String> preguntas1 = new ArrayList<>();
				for (int j = 0; j < preguntasArray1.length(); j++) {
					preguntas1.add(preguntasArray1.getString(j));
				}
				Encuesta e = new Encuesta(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado,actividadesPrevias, actividadesSeguimiento, preguntas1);
				encuestas.add(e);
			} else if (tipoActividad.equals("Recurso Educativo")) {
				String tipoRecurso = actividad.get("tipoRecurso").toString();
				RecursoEducativo r =new RecursoEducativo(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, 
						fechaLimite, resenas, calificacion, resultado,actividadesPrevias, actividadesSeguimiento, tipoRecurso);
				recursosEducativos.add(r);
			}
			
				
				
			}
			if (quizes.size() > 0) {
				System.out.println("Quizes cargados");
			}
			if (tareas.size() > 0) {
				System.out.println("Tareas cargadas");
			}
			if (examenes.size() > 0) {
				System.out.println("Examenes cargados");
			}
			if (encuestas.size() > 0) {
				System.out.println("Encuestas cargadas");
			}
			if (recursosEducativos.size() > 0) {
				System.out.println("Recursos Educativos cargados");
			}
			ArrayList<Actividad> actividades1 = new ArrayList<>();
			actividades1.addAll(quizes);
			actividades1.addAll(tareas);
			actividades1.addAll(examenes);
			actividades1.addAll(encuestas);
			actividades1.addAll(recursosEducativos);
			return actividades1;
			
		
		
	}
                
			
	
	

	@Override
	public void salvarActividad(String archivo, String actividadID, String descripcion, String objetivo, int nivelDificultad,
			int duracionEsperada, boolean esObligatoria, Date fechaLimite2, String resenas, double calificacion,
			int resultado,List<String>actividadesPrevias ,List<String> actividadesSeguimiento, String tipoActividad, Map<String, List<String>> preguntas) {
			
			
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
		        JSONArray preguntasArray = new JSONArray();
				for (String preguntaID : preguntas.keySet()) {
					JSONObject pregunta = new JSONObject();
					pregunta.put("preguntaID", preguntaID);
					JSONArray opcionesArray = new JSONArray();
					for (String opcion : preguntas.get(preguntaID)) {
						opcionesArray.put(opcion);
					}
					pregunta.put("opciones", opcionesArray);
					preguntasArray.put(pregunta);
				}
				newObject.put("preguntas", preguntasArray);
		        
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		       
	
	        

	}
	
	

}
