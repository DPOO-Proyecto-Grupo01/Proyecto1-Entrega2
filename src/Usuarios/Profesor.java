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
import Exceptions.NombreRepetido;
import Actividades.Pregunta;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;
import main.Central;

import static main.Central.actividades;



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
                               int resultado, int calificacion, String tipo, String learningPathID, List<String> actividadesPrevia, List<String> actividadesSeguimiento,
                               HashMap<String, Object> parametrosEspecificos, String actividadPrevia) throws NombreRepetido {
	if (mapaActividades.containsKey(actividadID)) {
		    throw new NombreRepetido("El nombre"+ actividadID + "ya");
	}
	else {
    // Crea una actividad
    Actividad actividad = null;
    
    if (tipo.equals("Quiz")) {
        double calificacionMinima = parametrosEspecificos.get("calificacionMinima") != null ? (Double) parametrosEspecificos.get("calificacionMinima") : 0.0;
        List<Pregunta> preguntas = parametrosEspecificos.get("preguntas") != null ? (List<Pregunta>) parametrosEspecificos.get("preguntas") : new ArrayList<>();
        String respuestaCorrecta = parametrosEspecificos.get("RespuestaCorrecta") != null ? (String) parametrosEspecificos.get("RespuestaCorrecta") : "";
        Quiz quiz = new Quiz(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                esObligatoria, fechaLimite, resenas, resultado, calificacion, actividadesPrevia,
                actividadesSeguimiento, preguntas, calificacionMinima, respuestaCorrecta);
        actividad = quiz;

    } else if (tipo.equals("Examen")) {
        double calificacionMinima = parametrosEspecificos.get("calificacionMinima") != null ? (Double) parametrosEspecificos.get("calificacionMinima") : 0.0;
        List<Pregunta> preguntas = parametrosEspecificos.get("preguntas") != null ? (List<Pregunta>) parametrosEspecificos.get("preguntas") : new ArrayList<>();
        Examen examen = new Examen(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                esObligatoria, fechaLimite, resenas, resultado, calificacion, actividadesPrevia, actividadesSeguimiento, preguntas, calificacionMinima);
        actividad = examen;

    } else if (tipo.equals("Encuesta")) {
        List<String> preguntas = parametrosEspecificos.get("preguntas") != null ? (List<String>) parametrosEspecificos.get("preguntas") : new ArrayList<>();
        Encuesta encuesta = new Encuesta(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                esObligatoria, fechaLimite, resenas, resultado, calificacion, actividadesPrevia, actividadesSeguimiento, preguntas);
        actividad = encuesta;

    } else if (tipo.equals("Recurso Educativo")) {
        String tipoRecurso = parametrosEspecificos.get("tipoRecurso") != null ? (String) parametrosEspecificos.get("tipoRecurso") : "";
        String linkRecurso = parametrosEspecificos.get("linkRecurso") != null ? (String) parametrosEspecificos.get("linkRecurso") : "";
        RecursoEducativo recurso = new RecursoEducativo(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                esObligatoria, fechaLimite, resenas, resultado, calificacion, actividadesPrevia, actividadesSeguimiento, tipoRecurso, linkRecurso);
        actividad = recurso;

    } else if (tipo.equals("Tarea")) {
        List<Pregunta> preguntas = parametrosEspecificos.get("preguntas") != null ? (List<Pregunta>) parametrosEspecificos.get("preguntas") : new ArrayList<>();
        String instrucciones = parametrosEspecificos.get("instrucciones") != null ? (String) parametrosEspecificos.get("instrucciones") : "";
        String estado = parametrosEspecificos.get("estado") != null ? (String) parametrosEspecificos.get("estado") : "";
        Tarea tarea = new Tarea(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                esObligatoria, fechaLimite, resenas, resultado, calificacion, actividadesPrevia, actividadesSeguimiento, preguntas, instrucciones, estado);
        actividad = tarea;
    }
    
    if (actividad != null) {
        mapaActividades.put(actividadID, actividad);
        LearningPath lp = learningPathsCreados.get(learningPathID);
        if (lp != null) {
            lp.setActividades(actividad);
        }
        actividades.add(actividad);
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
    
    public void revisarEstadoActividad(String actividadID, String LearningPathID) {
        LearningPath lp = learningPathsCreados.get(LearningPathID);
    	Actividad actividad = lp.actividades.get(actividadID);
        if (actividad.getTipoActividad().equals("Tarea") || actividad.getTipoActividad().equals("Examen")) {
            if (actividad.getEstado().equals("Enviado")) {
                actividad.setEstado("Exitoso");
            } else {
                actividad.setEstado("Fallido");
            }
        } else if (actividad.getTipoActividad().equals("RecursoEducativo") || actividad.getTipoActividad().equals("Encuesta")) {
            actividad.setEstado("Exitoso");
        } else if (actividad.getTipoActividad().equals("Quiz")) {
            if (actividad.getCalificacion() >= ((Quiz) actividad).getCalificacionMinima()) {
                actividad.setEstado("Exitoso");
            } else {
                actividad.setEstado("Fallido");
            }


        }
    }

    // ver el progreso de un estudiante
    public Map<String, String> verProgresoEstudiante(String estudianteID, String learningPathID) {
        LearningPath lp = learningPathsCreados.get(learningPathID);
        if (lp == null) {
            System.out.println("Learning Path not found: " + learningPathID);
            return new HashMap<>(); // Return an empty map or handle as needed
        }
        Progreso progreso = lp.obtenerProgresoEstudiante(estudianteID);
        if (progreso == null) {
            System.out.println("Progreso not found for estudianteID: " + estudianteID);
            return new HashMap<>(); // Return an empty map or handle as needed
        }
        return progreso.mostrarProgreso();
    }
    
    
    
    public List<Map> revisarFeedback (String learningpathId) {
    	LearningPath lp = learningPathsCreados.get(learningpathId);
    	List<Feedback> fb = lp.getFeedback();
    	List<Map> feedbacks = new ArrayList<>();
		for (Feedback f : fb) {
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