package Usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import Exceptions.LearningPathNoInscrito;
import Exceptions.NombreRepetido;
import Actividades.Pregunta;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;
import main.Central;



public class Profesor extends Usuario {
	
    public Map<String, LearningPath> learningPathsCreados;
    public Map<String, Actividad> mapaActividades;
    
    public String profesor = "Profesor";
    public Profesor(String UsuarioID, String nombre, String contraseña, String email, String tipoUsuario) {
        super(UsuarioID, nombre, contraseña, email, tipoUsuario);
        // TODO Auto-generated constructor stub
        this.learningPathsCreados = new HashMap<>();
        this.mapaActividades = new HashMap<>();
    }

    
    @Override
    public String getTipoUsuario() {
        return this.profesor;

    }

    public Map<String, LearningPath> getLearningPathsCreados() {
        return learningPathsCreados;
    }


    public void setLearningPathsCreados(Map<String, LearningPath> learningPathsCreados) {
        this.learningPathsCreados = learningPathsCreados;
    }


    public String getProfesor() {
        return profesor;
    }
    
    


	public Actividad crearActividad(String actividadID, String descripcion, String objetivo, int nivelDificultad,
	                               int duracionEsperada, boolean esObligatoria, Date fechaLimite, String resenas,
	                               double resultado, int calificacion, String tipo, String learningPathID, List<String> actividadesPrevia, List<String> actividadesSeguimiento,
	                               HashMap<String, Object> parametrosEspecificos, String actividadPrevia) throws NombreRepetido {
		if (mapaActividades.containsKey(actividadID)) {
			    throw new NombreRepetido("El nombre"+ actividadID + "ya");
		}
		else {
			
	    // Crea una actividad
	    Actividad actividad = null;
	    
	    if (tipo.equals("Quiz")) {
	        double calificacionMinima = parametrosEspecificos.get("calificacionMinima") != null ? (Double) parametrosEspecificos.get("calificacionMinima") : 0.0;
	        @SuppressWarnings("unchecked")
			List<Pregunta> preguntas = parametrosEspecificos.get("preguntas") != null ? (List<Pregunta>) parametrosEspecificos.get("preguntas") : new ArrayList<>();
	        String respuestaCorrecta = parametrosEspecificos.get("RespuestaCorrecta") != null ? (String) parametrosEspecificos.get("RespuestaCorrecta") : "";
	        Quiz quiz = new Quiz(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
	                esObligatoria, fechaLimite, resenas, resultado, calificacion, actividadesPrevia,
	                actividadesSeguimiento, preguntas, calificacionMinima, respuestaCorrecta);
	        
	        quiz.convertToJSONObject();
	        
	        actividad = quiz;
	
	    } else if (tipo.equals("Examen")) {
	        double calificacionMinima = parametrosEspecificos.get("calificacionMinima") != null ? (Double) parametrosEspecificos.get("calificacionMinima") : 0.0;
	        @SuppressWarnings("unchecked")
			List<Pregunta> preguntas = parametrosEspecificos.get("preguntas") != null ? (List<Pregunta>) parametrosEspecificos.get("preguntas") : new ArrayList<>();
	        Examen examen = new Examen(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
	                esObligatoria, fechaLimite, resenas, resultado, calificacion, actividadesPrevia, actividadesSeguimiento, preguntas, calificacionMinima);
	       
	        examen.convertToJSONObject();
	        
	        actividad = examen;
	
	    } else if (tipo.equals("Encuesta")) {
	        @SuppressWarnings("unchecked")
			List<String> preguntas = parametrosEspecificos.get("preguntas") != null ? (List<String>) parametrosEspecificos.get("preguntas") : new ArrayList<>();
	        Encuesta encuesta = new Encuesta(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
	                esObligatoria, fechaLimite, resenas, resultado, calificacion, actividadesPrevia, actividadesSeguimiento, preguntas);
	        
	        encuesta.convertToJSONObject();
	        
	        actividad = encuesta;
	
	    } else if (tipo.equals("RecursoEducativo")) {
	        String tipoRecurso = parametrosEspecificos.get("tipoRecurso") != null ? (String) parametrosEspecificos.get("tipoRecurso") : "";
	        String linkRecurso = parametrosEspecificos.get("linkRecurso") != null ? (String) parametrosEspecificos.get("linkRecurso") : "";
	        RecursoEducativo recurso = new RecursoEducativo(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
	                esObligatoria, fechaLimite, resenas, resultado, calificacion, actividadesPrevia, actividadesSeguimiento, tipoRecurso, linkRecurso);
	        
	        recurso.convertToJSONObject();
	        
	        actividad = recurso;
	        
	    } else if (tipo.equals("Tarea")) { 
	        @SuppressWarnings("unchecked")
			List<Pregunta> preguntas = parametrosEspecificos.get("preguntas") != null ? (List<Pregunta>) parametrosEspecificos.get("preguntas") : new ArrayList<>();
	        String instrucciones = parametrosEspecificos.get("instrucciones") != null ? (String) parametrosEspecificos.get("instrucciones") : "";
	        String estado = parametrosEspecificos.get("estado") != null ? (String) parametrosEspecificos.get("estado") : "Pendiente";
	        Tarea tarea = new Tarea(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
	                esObligatoria, fechaLimite, resenas, resultado, calificacion, actividadesPrevia, actividadesSeguimiento, preguntas, instrucciones, estado);
	        
	        tarea.convertToJSONObject();
	        
	        actividad = tarea;
	
	    }
	    
	    if (actividad != null) {
	        mapaActividades.put(actividadID, actividad);
	        System.out.println("Actividad creada con éxito"+ actividad);
	        System.out.println("profesor: "+ this);
	        LearningPath lp = learningPathsCreados.get(learningPathID);
	        System.out.println("Learning Path creados: " + learningPathsCreados);
	        System.out.println("Learning Path ID: " + learningPathID);
	        System.out.println("Learning Path: " + lp);
	        
	        
	        if (lp.actividades == null) {
	            lp.actividades = new HashMap<>();
	        }
	        System.out.println("Actividades: " + actividad);
	        System.out.println("Actividades ID"+ actividadID);
	        
	        lp.actividades.put(actividadID, actividad);
	        actividad.setActividadPrevia(actividadPrevia);
	
	    }
	    return actividad;
		}
	}

	public void CalificacionMinima(String actividadID, double calificacionMinima) {
	    Actividad actividad = mapaActividades.get(actividadID);
	    if (actividad != null) {
	        if (actividad.getTipoActividad().equals("Quiz")) {
	            ((Quiz) actividad).setCalificacionMinima(calificacionMinima);
	        } else if (actividad.getTipoActividad().equals("Examen")) {
	            ((Examen) actividad).setCalificacionMinima(calificacionMinima);
	        }
	    } else {
	        // Handle the case where actividad is null, if necessary
	    }
	} 
	



    public LearningPath crearLearningPath(String LearningPathID, String titulo, String descripcion, String objetivos,
                                  int nivelDificultad, int duracion, String profesorID, List<String> actividadesID, List<String> intereses) throws NombreRepetido {
    	if (learningPathsCreados.containsKey(LearningPathID)) {
    		   throw new NombreRepetido("El nombre"+ LearningPathID + "ya existe");
    		   
    	} else {
	        LearningPath learningPath = new LearningPath(LearningPathID, titulo, descripcion, objetivos, nivelDificultad, duracion, profesorID, actividadesID, intereses);
	        learningPathsCreados.put(LearningPathID, learningPath);
	        
	        return learningPath;
    	}
    }
    
    
	public String revisarEstadoActividad(String actividadID, String LearningPathID) {
		String mensaje = "";
		LearningPath lp = learningPathsCreados.get(LearningPathID);
		if (lp != null) {
			Actividad actividad = lp.actividades.get(actividadID);
			System.out.println("Actividad ID: " + actividadID);
			if (actividad != null) {
				if (actividad.getTipoActividad().equals("Tarea") || actividad.getTipoActividad().equals("Examen")) {
					System.out.println(actividad.getEstado());
					if ("Enviado".equals(actividad.getEstado())) {
						actividad.setEstado("Exitoso");
						mensaje = "Exitoso";
					} else {
						actividad.setEstado("Fallido");
						mensaje = "Fallido";
					}
				} else if (actividad.getTipoActividad().equals("RecursoEducativo")
						|| actividad.getTipoActividad().equals("Encuesta")) {
					actividad.setEstado("Exitoso");
					mensaje = "Exitoso";
				} else if (actividad.getTipoActividad().equals("Quiz")) {
					if (actividad.getCalificacion() >= ((Quiz) actividad).getCalificacionMinima()) {
						actividad.setEstado("Exitoso");
						mensaje = "Exitoso";
					} else {
						actividad.setEstado("Fallido");
						mensaje = "Fallido";
					}
				}
			} else {
				// Handle the case where actividad is null, if necessary
				if (lp.getActividades().isEmpty()) {
					System.out.println("No hay actividades");
					mensaje = "No hay actividades";
				}
				
			}
		} else {
			// Handle the case where lp is null, if necessary
			if (learningPathsCreados.isEmpty()) {
				System.out.println("No hay Learning Paths");
				mensaje = "No hay Learning Paths";
			}
		}
		
		return mensaje;
	}

    // ver el progreso de un estudiante
    
	public Map<String, String> verProgresoEstudiante(String estudianteID, String learningPathID) {
        LearningPath lp = learningPathsCreados.get(learningPathID);
        
        if (lp == null) {
            System.out.println("Learning Path not found: " + learningPathID);
            return new HashMap<>(); // Return an empty map or handle as needed
        }

        Progreso progreso = lp.getProgreso();
        
        if (progreso == null) {
            System.out.println("Progreso not found for estudianteID: " + estudianteID);
            return new HashMap<>(); // Return an empty map or handle as needed
        }
        return progreso.mostrarProgreso();
    } 
    
    
    
    public List<Map> revisarFeedback (String learningpathId) {
    	LearningPath lp = learningPathsCreados.get(learningpathId);
    	System.out.println("Learning Path: " + lp);
    	
    	List<Feedback> fb = lp.getFeedback();
    	System.out.println("Feedback: " + fb);
    	List<Map> feedbacks = new ArrayList<>();
		for (Feedback f : fb) {
			System.out.println("Feedback: " + f);
			feedbacks.add(f.mostrarFeedback());
		}
    	return feedbacks;
    	
    	
    }
    
	public double calcularRating(String learningpathId) {
		LearningPath lp = learningPathsCreados.get(learningpathId);
		List<Feedback> feedback = lp.getFeedback();
		double rating = 0;
		for (Feedback f : feedback) {
			rating += f.getCalificacion();
		}
		return rating / feedback.size();
	}

}