package Usuarios;

import java.util.Date;
import java.util.List;
import java.util.Map;

import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;

public class Profesor extends Usuario {
	public Profesor(String UsuarioID, String nombre, String contraseña, String email, String tipoUsuario) {
		super(UsuarioID, nombre, contraseña, email, tipoUsuario);
		// TODO Auto-generated constructor stub
	}
	
	private Map<String, List<LearningPath>> RecomendacionesProfesor;
	private Map<String, LearningPath> learningPathsCreados;
	public String profesor = "Profesor";

	@Override
	public String getTipoUsuario() {
		return this.profesor;

	}
	
	
	public Map<String, List<LearningPath>> getRecomendacionesProfesor() {
		return RecomendacionesProfesor;
	}



	public void setRecomendacionesProfesor(Map<String, List<LearningPath>> recomendacionesProfesor) {
		RecomendacionesProfesor = recomendacionesProfesor;
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



	public void revisarEstadoActividad(Actividad actividad, String estado ) {
		// Revisa el estado de una actividad
		if (actividad.getTipoActividad().equals("Tarea") || actividad.getTipoActividad().equals("Examen")) {
			if(actividad.getEstado().equals("Enviado")) {
				actividad.setEstado("Exitoso");
			} else {
				actividad.setEstado("Fallido");
			}
		} else if (actividad.getTipoActividad().equals("RecursoEducativo") || actividad.getTipoActividad().equals("Encuesta")) {
            actividad.setEstado("Exitoso");
        } else if (actividad.getTipoActividad().equals("Quiz")){
			if (actividad.getCalificacion() >= ((Quiz) actividad).getCalificacionMinima()) {
				actividad.setEstado("Exitoso");
			} else {
				actividad.setEstado("Fallido");
			}
        	
			
		} 
	}
		
	
	public void crearActividad(String actividadID, String descripcion, String objetivo, int nivelDificultad,
			int duracionEsperada, boolean esObligatoria, Date fechaLimite, String resenas, double calificacion,
			int resultado, String tipo, String learningPathID, List<String> actividadesPrevia, List<String> actividadesSeguimiento) {
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
			
			
			Actividad recurso = new RecursoEducativo (actividadID, descripcion, objetivo,nivelDificultad, duracionEsperada,
					esObligatoria,fechaLimite,  resenas, calificacion, resultado);
			actividad = recurso;
		}
		
		else if (tipo.equals("Tarea")){
			
	
			Actividad tarea = new Tarea (actividadID, descripcion, objetivo,nivelDificultad, duracionEsperada,
					esObligatoria,fechaLimite,  resenas, calificacion, resultado);
			
			actividad = tarea;
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
	
	
	public void crearLearningPath(String learningPathID, String titulo, String descripcion, String objetivos,
			int nivelDificultad, int duracion, Profesor profesor, List<Actividad> actividades, List<String> intereses) {
		LearningPath learningPath = new LearningPath(learningPathID, titulo, descripcion, objetivos, nivelDificultad, duracion, profesor, actividades, intereses);
		learningPathsCreados.put(learningPathID, learningPath);
	}
	
	
	
	public void agregarRecomendacion(Map<String, List<LearningPath>> recomendaciones, String interes, LearningPath learningPath) {
        List<LearningPath> learningpaths = recomendaciones.get(interes);
		learningpaths.add(learningPath);
    }
	
	// ver el progreso de un estudiante
	public Map<String, String > verProgresoEstudiante(String estudianteID, LearningPath learningPath) {
		Progreso progreso = learningPath.obtenerProgresoEstudiante(estudianteID);
		return progreso.mostrarProgreso();
	}
}