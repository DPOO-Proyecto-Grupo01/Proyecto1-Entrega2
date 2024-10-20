package Perisistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
	public ArrayList<Quiz> cargarQuizes(String archivo) throws Exception{
		System.out.println("Cargando Quizes");
		String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
		JSONArray quizes = new JSONArray(jsonCompleto);
		ArrayList<Quiz> lista = new ArrayList<Quiz>();
		
		for (int i = 0; i < quizes.length(); i++) {
	           JSONObject actividad = quizes.getJSONObject(i);
	           String actividadID = actividad.get("actividadID").toString();
	           String descripcion = actividad.get("descripcion").toString();
	           String objetivo = actividad.get("objetivo").toString();
	           int nivelDificultad = actividad.getInt("nivelDificultad");
	           int duracionEsperada = actividad.getInt("duracionEsperada");
	           boolean esObligatoria = actividad.getBoolean("esObligatoria");
	           // Convertir la fecha de string a date
	           String fechaLimiteStr = actividad.getString("fechaLimite2");  // Obtener la fecha como cadena
	           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	           LocalDateTime fechaLimiteD = LocalDateTime.parse(fechaLimiteStr, formatter);  // Parsear la fecha
	           Date fechaLimite = Date.from(fechaLimiteD.atZone(ZoneId.systemDefault()).toInstant());
	           
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
				//Preguntas tiene este formato: "preguntas": [
	            //{
	            //    "pregunta": "¿Qué método se utiliza para imprimir en consola?",
	            //    "opciones": ["print()", "console.log()", "System.out.println()", "echo()"]
	            //}
	        //]
				
				
				String tipoActividad = actividad.get("tipoActividad").toString();
				if (tipoActividad.equals("quiz")) {
					double calificacionQuiz = actividad.getDouble("calificacionMinima");
					Map<String, List<String>> preguntasArray1 = new HashMap<>();
					JSONArray preguntasArray = actividad.getJSONArray("preguntas");
					for (int j = 0; j < preguntasArray.length(); j++) {
						JSONObject pregunta = preguntasArray.getJSONObject(j);
						String preguntaID = pregunta.get("pregunta").toString();
						JSONArray opcionesArray = pregunta.getJSONArray("opciones");
						List<String> opciones = new ArrayList<>();
						for (int k = 0; k < opcionesArray.length(); k++) {
							opciones.add(opcionesArray.getString(k));
						}
						preguntasArray1.put(preguntaID, opciones);
					}
					
					Quiz quiz = new Quiz(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevias, actividadesSeguimiento, preguntasArray1, calificacionQuiz, tipoActividad);
					lista.add(quiz);
				}
		}
		
		
		return lista;
		
	}
	
	@Override
	public ArrayList<Examen> cargarExamenes(String archivo)throws Exception{
	System.out.println("Cargando Quizes");
	String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
	JSONArray Examen = new JSONArray(jsonCompleto);
	ArrayList<Examen> lista = new ArrayList<Examen>();
	
	for (int i = 0; i < Examen.length(); i++) {
           JSONObject actividad = Examen.getJSONObject(i);
           String actividadID = actividad.get("actividadID").toString();
           String descripcion = actividad.get("descripcion").toString();
           String objetivo = actividad.get("objetivo").toString();
           int nivelDificultad = actividad.getInt("nivelDificultad");
           int duracionEsperada = actividad.getInt("duracionEsperada");
           boolean esObligatoria = actividad.getBoolean("esObligatoria");
           // Convertir la fecha de string a date
           String fechaLimiteStr = actividad.getString("fechaLimite2");  // Obtener la fecha como cadena
           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
           LocalDateTime fechaLimiteD = LocalDateTime.parse(fechaLimiteStr, formatter);  // Parsear la fecha
           Date fechaLimite = Date.from(fechaLimiteD.atZone(ZoneId.systemDefault()).toInstant());
           
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
			
			
			
			if (tipoActividad.equals("Examen")) {
				JSONArray preguntasArray = actividad.getJSONArray("preguntas");
			    Map<String, List<String>> preguntas = new HashMap<>();
				    for (int j = 0; j < preguntasArray.length(); j++) {
	                JSONObject pregunta = preguntasArray.getJSONObject(j);
	                String preguntaID = pregunta.get("pregunta").toString();
	                JSONArray opcionesArray = pregunta.getJSONArray("opciones");
	                List<String> opciones = new ArrayList<>();
	                for (int k = 0; k < opcionesArray.length(); k++) {
	                    opciones.add(opcionesArray.getString(k));
	                }
	                preguntas.put(preguntaID, opciones);
	            }
				double calificacionQuiz = actividad.getDouble("calificacionMinima");
				Examen ex = new Examen(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesPrevias, actividadesSeguimiento, preguntas, calificacionQuiz, tipoActividad);
				lista.add(ex);
			}
	}
	
	
	return lista;
	}
	
	public ArrayList<Tarea> cargarTareas(String archivo)throws Exception{
		String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
		JSONArray tareas = new JSONArray(jsonCompleto);
		ArrayList<Tarea> lista = new ArrayList<Tarea>();
		
		for (int i = 0; i < tareas.length(); i++) {
	           JSONObject actividad = tareas.getJSONObject(i);
	           String actividadID = actividad.get("actividadID").toString();
	           String descripcion = actividad.get("descripcion").toString();
	           String objetivo = actividad.get("objetivo").toString();
	           int nivelDificultad = actividad.getInt("nivelDificultad");
	           int duracionEsperada = actividad.getInt("duracionEsperada");
	           boolean esObligatoria = actividad.getBoolean("esObligatoria");
	           // Convertir la fecha de string a date
	           String fechaLimiteStr = actividad.getString("fechaLimite2");  // Obtener la fecha como cadena
	           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	           LocalDateTime fechaLimiteD = LocalDateTime.parse(fechaLimiteStr, formatter);  // Parsear la fecha
	           Date fechaLimite = Date.from(fechaLimiteD.atZone(ZoneId.systemDefault()).toInstant());
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
				
                	
                	if (tipoActividad.equals("Tarea")) {
                		String estado = actividad.get("estado").toString();
            			
        			    JSONArray preguntasArray = actividad.getJSONArray("preguntas");
        			    Map<String, List<String>> preguntas = new HashMap<>();
        				    for (int j = 0; j < preguntasArray.length(); j++) {
                            JSONObject pregunta = preguntasArray.getJSONObject(j);
                            String preguntaID = pregunta.get("preguntaID").toString();
                            JSONArray opcionesArray = pregunta.getJSONArray("respuestas");
                            List<String> opciones = new ArrayList<>();
                            for (int k = 0; k < opcionesArray.length(); k++) {
                                opciones.add(opcionesArray.getString(k));
                            }
                            preguntas.put(preguntaID, opciones);
                        }
                        	String InstruccionesArray = actividad.get("Instrucciones").toString();
	                	Tarea tarea = new Tarea(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado,
	                			actividadesPrevias, actividadesSeguimiento, preguntas, InstruccionesArray, tipoActividad, estado);
	                    lista.add(tarea);
                	}
				
					
				}
		return lista;
		}
	
	@Override
	public ArrayList<Encuesta> cargarEncuestas(String archivo)throws Exception{
		System.out.println("Cargando Encuestas");
		String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
		JSONArray encuestas = new JSONArray(jsonCompleto);
		ArrayList<Encuesta> lista = new ArrayList<Encuesta>();
		
		
		
		for (int i = 0; i < encuestas.length(); i++) {
	           JSONObject actividad = encuestas.getJSONObject(i);
	           String actividadID = actividad.get("actividadID").toString();
	           String descripcion = actividad.get("descripcion").toString();
	           String objetivo = actividad.get("objetivo").toString();
	           int nivelDificultad = actividad.getInt("nivelDificultad");
	           int duracionEsperada = actividad.getInt("duracionEsperada");
	           boolean esObligatoria = actividad.getBoolean("esObligatoria");
	           // Convertir la fecha de string a date
	           String fechaLimiteStr = actividad.getString("fechaLimite2");  // Obtener la fecha como cadena
	           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	           LocalDateTime fechaLimiteD = LocalDateTime.parse(fechaLimiteStr, formatter);  // Parsear la fecha
	           Date fechaLimite = Date.from(fechaLimiteD.atZone(ZoneId.systemDefault()).toInstant());
	           
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
				
				
				if (tipoActividad.equals("Encuesta")) {
					JSONArray preguntasArray1 = actividad.getJSONArray("preguntas");
					List<String> preguntas1 = new ArrayList<>();
					for (int j = 0; j < preguntasArray1.length(); j++) {
						preguntas1.add(preguntasArray1.getString(j));
					}
					Encuesta e = new Encuesta(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas, calificacion, resultado,actividadesPrevias, actividadesSeguimiento, preguntas1, tipoActividad);
					lista.add(e);
				}	
		}
		
		return lista;
	}
	
	
	@Override
	public ArrayList<RecursoEducativo> cargarRecursosEducativos(String archivo) throws IOException{
		System.out.println("Cargando Recursos Educativos");
		String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
		JSONArray recursosEducativos = new JSONArray(jsonCompleto);
		ArrayList<RecursoEducativo> lista = new ArrayList<RecursoEducativo>();
		
		for (int i = 0; i < recursosEducativos.length(); i++) {
	           JSONObject actividad = recursosEducativos.getJSONObject(i);
	           String actividadID = actividad.get("actividadID").toString();
	           String descripcion = actividad.get("descripcion").toString();
	           String objetivo = actividad.get("objetivo").toString();
	           int nivelDificultad = actividad.getInt("nivelDificultad");
	           int duracionEsperada = actividad.getInt("duracionEsperada");
	           boolean esObligatoria = actividad.getBoolean("esObligatoria");
	           // Convertir la fecha de string a date
	           String fechaLimiteStr = actividad.getString("fechaLimite2");  // Obtener la fecha como cadena
	           DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
	           LocalDateTime fechaLimiteD = LocalDateTime.parse(fechaLimiteStr, formatter);  // Parsear la fecha
	           Date fechaLimite = Date.from(fechaLimiteD.atZone(ZoneId.systemDefault()).toInstant());
	           
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
				
				
				
				if(tipoActividad.equals("recurso educativo"));
				String tipoRecurso = actividad.get("tipoRecurso").toString();
				String linkRecurso = actividad.get("linkRecurso").toString();
				RecursoEducativo r =new RecursoEducativo(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, 
						fechaLimite, resenas, calificacion, resultado,actividadesPrevias, actividadesSeguimiento, tipoRecurso, tipoActividad, linkRecurso);
				lista.add(r);
		}
		
		
		return lista;
	}
	
	

	@Override
	public void salvarActividad(String archivo, String actividadID, String descripcion, String objetivo, int nivelDificultad,
			int duracionEsperada, boolean esObligatoria, Date fechaLimite2, String resenas, double calificacion,
			int resultado,List<String>actividadesPrevias ,List<String> actividadesSeguimiento, String tipoActividad, 
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
