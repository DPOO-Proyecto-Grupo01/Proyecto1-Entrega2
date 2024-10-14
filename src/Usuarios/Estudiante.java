package Usuarios;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import Actividades.Actividades;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;

public class Estudiante extends Usuario {
	
	public Estudiante(String UsuarioID, String contraseña, String nombre, String email) {
		super(UsuarioID, nombre, contraseña, email);
        // TODO Auto-generated constructor stub
    }
	

	private List<LearningPath> learningPathsInscritos;
	private Map<Actividades, Progreso> progresoActividades;
	private List<String> intereses;
	private String ProfesorAsignado;
	public String estudiante = "Estudiante";

	@Override
	public String getTipoUsuario() {
		return this.estudiante;
	}
	
	public void inscribirLearningPath(LearningPath learningPath) {
		learningPathsInscritos.add(learningPath);
	}
	
	public void completarActividad(Actividades actividad) {
		// crea una lista de actividades completadas

		List<Actividades> actividadesCompletadas = new ArrayList<>();
		if(progresoActividades.get(actividad).getEstado()==100.0){
			actividadesCompletadas.add(actividad);
		}
		
	}
	
	public void verProgreso(Actividades actividad, Progreso progreso) {
		progresoActividades.put(actividad, progreso);
	}
	
	public void recibirRecomendacion(LearningPath learningPath) {
		//Devuelve una lista de Learning Paths recomendados para el estudiante, basados en sus intereses. Que se le asigne learning oaths de su profesor asignado
		Map<String, List<String>> mapaR = LearningPath.getRecomendacionProfesores();
		
		if (mapaR.containsKey(ProfesorAsignado) && mapaR.get(ProfesorAsignado).contains(intereses)) {
			System.out.println("Recomendacion: " + learningPath);
		}
		
	}
	
	public void enviarFeedback(Feedback feedback) {
		// Envia un feedback a un Learning Path
		Feedback.getFeedback().add(feedback);
	}
	
	public void verActividadesDisponibles(List<Actividades> actividades, LearningPath learningPath) {
		// Muestra las actividades disponibles en los Learning Paths inscritos.
		if (progresoActividades.get(actividades).getEstado() != 100.0) {
			System.out.println("Actividades disponibles: " + actividades);
		}
	}
}