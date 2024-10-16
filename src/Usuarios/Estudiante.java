package Usuarios;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import Actividades.Actividad;
import Actividades.Tarea;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;

public class Estudiante extends Usuario {
	
	public Estudiante(String UsuarioID, String nombre, String contraseña, String email, String tipoUsuario) {
		super(UsuarioID, nombre, contraseña, email, tipoUsuario);
        // TODO Auto-generated constructor stub
    }
	

	private List<LearningPath> learningPathsInscritos;
	private Map<Actividad, Progreso> progresoActividades;
	private List<String> intereses;
	private String ProfesorAsignado;
	public String estudiante = "Estudiante";
	private Profesor profesor;
	
	
	@Override
	public String getTipoUsuario() {
		return this.estudiante;
	}
	
	public void inscribirLearningPath(LearningPath learningPath) {
		learningPathsInscritos.add(learningPath);
	}
	
	public void completarActividad(Actividad actividad, Tarea tarea) {
		// crea una lista de actividades completadas

		List<Actividad> actividadesCompletadas = new ArrayList<>();
		if(progresoActividades.get(actividad).getEstado()==100.0){
			actividadesCompletadas.add(actividad);
			actividad.setEstado("Completada");
			
			
		}
		
	}
	
	public void verProgreso(Actividad actividad, Progreso progreso) {
		progresoActividades.put(actividad, progreso);
	}
	
	public List<LearningPath> recibirRecomendacion() {
		//Devuelve una lista de Learning Paths recomendados para el estudiante, basados en sus intereses. Que se le asigne learning oaths de su profesor asignado
		//crear lista vacia
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
	
}
