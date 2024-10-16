package Usuarios;

import java.util.List;
import java.util.Map;

import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import LearningPaths.LearningPath;

public class Profesor extends Usuario {
	public Profesor(String UsuarioID, String contraseña) {
		super(UsuarioID, contraseña, contraseña, contraseña);
		// TODO Auto-generated constructor stub
	}

	private Map<String, LearningPath> learningPathsCreados;
	public String profesor = "Profesor";
	
	@Override
	public String getTipoUsuario() {
		return this.profesor;

	}
	
	public void revisarEstadoActividad(Actividad actividad, String estado ) {
		// Revisa el estado de una actividad
		if (actividad.getTipoActividad().equals("Tarea") || actividad.getTipoActividad().equals("Examen")) {
			if(actividad.EstadoActual(estado).equals("Enviado")) {
				actividad.EstadoActual("Exitoso");
			} else {
				actividad.EstadoActual("Fallido");
			}
		} else if (actividad.getTipoActividad().equals("RecursoEducativo") || actividad.getTipoActividad().equals("Encuesta")) {
            actividad.EstadoActual("Exitoso");
        } else if (actividad.getTipoActividad().equals("Quiz")){
			if (actividad.getCalificacion() >= ((Quiz) actividad).getCalificacionMinima()) {
				actividad.EstadoActual("Exitoso");
			} else {
				actividad.EstadoActual("Fallido");
			}
        	
			
		} 
	}
		
	
	public void crearActividad(String actividadID, String descripcion, String objetivo, int nivelDificultad,
			int duracionEsperada, boolean esObligatoria, int fechaLimite, String resenas, double calificacion,
			int resultado, String tipo, String learningPathID) {
		// Crea una actividad
		Actividad actividad = null;
		
		if (tipo.equals("Quiz")) {
			Actividad quiz = new Quiz (actividadID, descripcion, objetivo,nivelDificultad, duracionEsperada,
					esObligatoria,fechaLimite,  resenas, calificacion, resultado);
			
			actividad = quiz;
			}
			
		else if (tipo.equals("Examen")) {
			Actividad examen = new Examen ( actividadID,descripcion, objetivo,nivelDificultad,duracionEsperada,
					esObligatoria, fechaLimite, resenas, calificacion, resultado);
			
			actividad = examen;
					}
			
		else if (tipo.equals("Encuesta")) {
			Actividad encuesta = new Encuesta (actividadID, descripcion, objetivo,nivelDificultad, duracionEsperada,
					esObligatoria,fechaLimite,  resenas, calificacion, resultado);
			actividad = encuesta;
		}
		
		else if (tipo.equals("Recurso Educativo")) {
			
			//String tipoR= input??;
			//Actividad recurso = new RecursoEducativo (actividadID, descripcion, objetivo,nivelDificultad, duracionEsperada,
					//esObligatoria,fechaLimite,  resenas, calificacion, resultado, tipo);
			//actividad = recurso;
		}
		
		else if (tipo.equals("Tarea")){
			//String instrucciones = input??;
	
			//Actividad tarea = new Tarea (actividadID, descripcion, objetivo,nivelDificultad, duracionEsperada,
					//esObligatoria,fechaLimite,  resenas, calificacion, resultado, tipo, instrucciones);
			
			//actividad = tarea;
		}
		
		LearningPath lp = learningPathsCreados.get(learningPathID);
		
		lp.setActividades(actividad);
		
	}
	
	public void CalificacionMinima(Actividad actividad, double calificacionMinima) {
		if(actividad.getTipoActividad().equals("Quiz")) {
			((Quiz) actividad).setCalificacionMinima(calificacionMinima);
		} else {
			if (actividad.getTipoActividad().equals("Examen")) {
				((Examen) actividad).setCalificacionMinima(calificacionMinima);
			}
		}
		
		
	}

	}