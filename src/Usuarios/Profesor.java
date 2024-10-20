package Usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

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
	
	public Map<String, List<LearningPath>> RecomendacionesProfesor;
	public Map<String, LearningPath> learningPathsCreados;
	public String profesor = "Profesor";
	public Map<String, Actividad> actividades;

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
		List<Actividad> actividadesParametro = new ArrayList<>();
		
		for (String actividadPrevia: actividadesPrevia) {
			Actividad anterior = actividades.get(actividadPrevia);
			actividad.setActividadesPrevia(anterior);
			actividadesParametro.add(anterior);
		}
		
		if (tipo.equals("Quiz")) {
			Quiz quiz = new Quiz (actividadID, descripcion, objetivo,nivelDificultad, duracionEsperada,
					esObligatoria,fechaLimite,  resenas, calificacion, resultado, actividadesParametro, actividadesSeguimiento, null, calificacion);
			System.out.println("Ingrese la calificacion minima");
	        Scanner scanner = new Scanner(System.in);
			double min = scanner.nextDouble();
			scanner.close();
			quiz.setCalificacionMinima(min);
			actividad = quiz;
			
			}
			
		else if (tipo.equals("Examen")) {
			Examen examen = new Examen ( actividadID,descripcion, objetivo,nivelDificultad,duracionEsperada,
					esObligatoria, fechaLimite, resenas, calificacion, resultado, actividadesParametro, actividadesSeguimiento, actividadesSeguimiento, calificacion);
			
			actividad = examen;
					}
			
		else if (tipo.equals("Encuesta")) {
			Encuesta encuesta = new Encuesta (actividadID, descripcion, objetivo,nivelDificultad, duracionEsperada,
					esObligatoria,fechaLimite,  resenas, calificacion, resultado, actividadesParametro, actividadesSeguimiento, actividadesSeguimiento);
			actividad = encuesta;
		}
		
		else if (tipo.equals("Recurso Educativo")) {
			
			
			RecursoEducativo recurso = new RecursoEducativo (actividadID, descripcion, objetivo,nivelDificultad, duracionEsperada,
					esObligatoria,fechaLimite,  resenas, calificacion, resultado, actividadesParametro, actividadesSeguimiento, learningPathID, learningPathID);
			actividad = recurso;
		}
		
		else if (tipo.equals("Tarea")){
			
	
			Tarea tarea = new Tarea (actividadID, descripcion, objetivo,nivelDificultad, duracionEsperada,
					esObligatoria,fechaLimite,  resenas, calificacion, resultado, actividadesParametro, actividadesSeguimiento, learningPathID, learningPathID, actividadesSeguimiento);
			
			actividad = tarea;
		}
		
		
		
		LearningPath lp = learningPathsCreados.get(learningPathID);
		
		lp.setActividades(actividad);
		actividades.put(actividadID, actividad);
		
		
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
	
	
	public void crearLearningPath(String LearningPathID, String titulo, String descripcion, String objetivos,
			int nivelDificultad, int duracion, String profesorID, List<String> actividadesID, List<String> intereses) {
		LearningPath learningPath = new LearningPath(LearningPathID, titulo, descripcion, objetivos, nivelDificultad, duracion, profesorID, actividadesID, intereses);
		learningPathsCreados.put(LearningPathID, learningPath);
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