package Usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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

public class Estudiante extends Usuario {
	private List<LearningPath> learningPathsInscritos;
	private List<String> intereses;
	private String ProfesorAsignado;
	public String estudiante = "Estudiante";
	private HashMap<String,Profesor> profesores = new HashMap<>();
	private Map<LearningPath, Progreso> progresoLearningPath;
	private HashMap<String, Actividad> actividades = new HashMap<>();
	
	public Estudiante(String UsuarioID, String nombre, String contraseña, String email, String tipoUsuario) {
		super(UsuarioID, nombre, contraseña, email, tipoUsuario);
        
    }
	

	
	
	public HashMap<String, Actividad> getActividades() {
		return actividades;
	}

	@Override
	public String getTipoUsuario() {
		return this.estudiante;
	}
	
	public void inscribirLearningPath(String LearningPathID, String profesorID) {
		Profesor profesor = profesores.get(profesorID);
		LearningPath learningPath = profesor.getLearningPathsCreados().get(LearningPathID);
		learningPathsInscritos.add(learningPath);
		learningPath.estudiantesInscritos.put(usuarioID, this);
	}
	
	public void completarActividad(Actividad actividad) {
		// crea una lista de actividades completadas

		List<String> actividadesPrevias = actividad.getActividadesPrevias();
		for (String actividadPrevia : actividadesPrevias) {
			if (actividadPrevia.equals(null)) {
				System.out.println("Tenga cuidado no ha realizado las actividades previas");
				break;
			}
		}
		
		if (actividad.getTipoActividad().equals("Tarea")) {
			Tarea tarea = (Tarea) actividad;
			tarea.completarTarea();
			tarea.setEstado("Enviado");
		}
		else if(actividad.getTipoActividad().equals("Examen")) {
            Examen examen = (Examen) actividad;
            examen.completarExamen();
		}
		else if (actividad.getTipoActividad().equals("Recurso Educativo")) {
			RecursoEducativo recurso = (RecursoEducativo) actividad;
			recurso.completarRecurso();
			recurso.setEstado("Enviado");
		} 
		else if (actividad.getTipoActividad().equals("Quiz")) {
			Quiz quiz = (Quiz) actividad;
			quiz.completarQuiz();
			if (quiz.isAprobado()) {
				quiz.setEstado("Exitoso");
			} else {
				quiz.setEstado("Fallido");
			}
		} 
		else if (actividad.getTipoActividad().equals("Encuesta")) {
			Encuesta encuesta = (Encuesta) actividad;
			encuesta.completarEncuesta();
			encuesta.setEstado("Exitoso");
		}
		
		String recomendacion = ((Actividad) actividad.getActividadesSeguimiento()).getActividadID();
		System.out.println("Se le recomienda realizar la actividad: " + recomendacion);
	}
	
	
	public void verProgresoLearningPath(LearningPath learningPath, Progreso progreso) {
		progresoLearningPath.put(learningPath, progreso);
	}
	
	public List<LearningPath> recibirRecomendacion() {
		//Devuelve una lista de Learning Paths recomendados para el estudiante, basados en sus intereses. Que se le asigne learning oaths de su profesor asignado
		//crear lista vacia
		Profesor profesor = profesores.get(ProfesorAsignado);
		List<LearningPath> learningPathsRecomendados = new ArrayList<>();
		for (String interes : intereses) {
			if (profesor.getRecomendacionesProfesor().containsKey(interes)) {
				List<LearningPath> learningPaths= profesor.getRecomendacionesProfesor().get(interes);
				learningPathsRecomendados.addAll(learningPaths);
			}
			
		}
		return learningPathsRecomendados;
		
		
	}
	
	
	
	public List<Actividad> actividadesDisponibles(List<Actividad> actividades, List<LearningPath> Lista) {
		// Muestra las actividades disponibles en los Learning Paths inscritos.
		List<Actividad> actividadesDisponibles = new ArrayList<>();
		for (LearningPath learningPath : Lista) {
			 actividadesDisponibles.addAll(learningPath.getActividades());
			
		}
		return actividadesDisponibles;
	}

		
		
	
	public double getProgresoLearningPath(LearningPath learningPath) {
		// Calcula el progreso del estudiante en un Learning Path
		return learningPath.getProgreso().getPorcentajeDeExito();
		
	}
	
	public void enviarFeedback(LearningPath learningPath, String feedback, double calificacion, 
			String comentario, Date fecha, String feedbackID ) {
		// Envia un feedback al profesor del Learning Path
        Feedback feedbackEstudiante= new Feedback(feedbackID, comentario, calificacion, fecha, this, learningPath);
        learningPath.getFeedback().add(feedbackEstudiante);
    
		
	}
	
	public void crearProgreso(LearningPath learningPath, Date fechaInicio, Date fechaCompletado, int tiempoDedicado,
			double estado) {
		// Crea un registro de progreso para una actividad
		int random = (int) (Math.random() * 1000 + 1);
		String randomString = Integer.toString(random);
		Progreso progreso = new Progreso(randomString, this.usuarioID, learningPath, fechaInicio, fechaCompletado, tiempoDedicado, estado);
		progresoLearningPath.put(learningPath, progreso);
	}
	
	
	
	
}
