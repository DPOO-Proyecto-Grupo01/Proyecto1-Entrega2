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
	private Map<String,LearningPath> learningPathsInscritos = new HashMap<>();
	private List<String> intereses;
	public String estudiante = "Estudiante";
	public HashMap<String,Profesor> profesores = new HashMap<>();
	private Map<LearningPath, Progreso> progresoLearningPath = new HashMap<>();
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
	
	public int inscribirLearningPath(String LearningPathID, String profesorID) {
		
		Profesor profesor = profesores.get(profesorID);
		LearningPath learningPath = profesor.getLearningPathsCreados().get(LearningPathID);
		learningPathsInscritos.put(LearningPathID,learningPath);
		learningPath.estudiantesInscritos.put(usuarioID, this);
		List<String> actividades = learningPath.getActividadesID();
		Progreso progreso = crearProgreso(learningPath, new Date(), new Date(), 0, 0);
		progresoLearningPath.put(learningPath, progreso);
		
		for (String actividadID : actividades) { 
			Actividad actividad = learningPath.actividades.get(actividadID);
			this.actividades.put(actividadID, actividad);
		}
		
		return learningPath.estudiantesInscritos.size();
	}
	
	public void completarActividad(String actividadID, String learningPathID) {
        LearningPath learningPath = learningPathsInscritos.get(learningPathID);
        Map<String, Actividad> mapa = learningPath.getActividades();
        Actividad actividad = mapa.get(actividadID);
        List<String> actividadesPrevias = actividad.getActividadesPrevias();
        ArrayList<String> actividadesPrevias2 = new ArrayList<String>();
        for (String actividadPrevia : actividadesPrevias) {
        	Actividad act = learningPath.getActividades().get(actividadPrevia);
            if (act.getEstado()== null ) {
            	actividadesPrevias2.add(act.getActividadID());
                
            }
        }
        System.out.println("Tenga cuidado no ha realizado las actividades previas: " + actividadesPrevias2);

        if (actividad.getTipoActividad().equals("Tarea")) {
            Tarea tarea = (Tarea) actividad;
            tarea.completarTarea();
            tarea.setEstado("Enviado");
        } else if (actividad.getTipoActividad().equals("Examen")) {
            Examen examen = (Examen) actividad;
            examen.completarExamen();
        } else if (actividad.getTipoActividad().equals("Recurso Educativo")) {
            RecursoEducativo recurso = (RecursoEducativo) actividad;
            recurso.completarRecurso();
            recurso.setEstado("Enviado");
        } else if (actividad.getTipoActividad().equals("Quiz")) {
            Quiz quiz = (Quiz) actividad;
            quiz.completarQuiz();
            quiz.setRespuestaUsuario("A");
            if (quiz.isAprobado()) {
                quiz.setEstado("Exitoso");
            } else {
                quiz.setEstado("Fallido");
            }
        } else if (actividad.getTipoActividad().equals("Encuesta")) {
            Encuesta encuesta = (Encuesta) actividad;
            encuesta.completarEncuesta();
            encuesta.setEstado("Exitoso");
        }

        List<String> actividadSeguimiento =  actividad.getActividadesSeguimiento();
        Progreso progreso = progresoLearningPath.get(learningPath);
        List<Actividad> actividades = learningPath.actividades.values().stream().toList();
        learningPath.actualizarProgreso(progreso, actividades);
        System.out.println("Se le recomienda realizar la actividad: " + actividadSeguimiento);
    }
	
	public List<LearningPath> recibirRecomendacion(String profesorID) {
		//Devuelve una lista de Learning Paths recomendados para el estudiante, basados en sus intereses. Que se le asigne learning oaths de su profesor asignado
		//crear lista vacia
		Profesor profesor = profesores.get(profesorID);
		List<LearningPath> learningPathsRecomendados = new ArrayList<>();
		for (String interes : intereses) {
			if (profesor.getRecomendacionesProfesor().containsKey(interes)) {
				List<LearningPath> learningPaths= profesor.getRecomendacionesProfesor().get(interes);
				learningPathsRecomendados.addAll(learningPaths);
			}
			
		}
		return learningPathsRecomendados;
		
		
	}
	
	
	
	public List<Actividad> actividadesDisponibles(String learningPathID) {
		// Muestra las actividades disponibles en los Learning Paths inscritos.
		List<String> actividades = learningPathsInscritos.get(learningPathID).getActividadesID();
		
		List<Actividad> actividadesDisponibles = new ArrayList<>();
		LearningPath learningPath = learningPathsInscritos.get(learningPathID);
		Map<String,Actividad> mapa = learningPath.getActividades();
		for (String actividadID : actividades) {
			Actividad actividad = mapa.get(actividadID);
			
			if(actividad.getEstado() == null) {
				actividadesDisponibles.add(actividad);
			}
		}
		return actividadesDisponibles;
	}

		
		
	
	 public double getProgresoLearningPath(String learningPathID) {
	        LearningPath learningPath = learningPathsInscritos.get(learningPathID);
	        Progreso progreso = learningPath.getProgreso();
	        if (progreso != null) {
	            return progreso.getPorcentajeDeExito();
	        } else {
	            System.out.println("Progreso no encontrado para el LearningPath: " + learningPathID);
	            return 0.0;
	        }
	    }
	 
	public void enviarFeedback(LearningPath learningPath, String feedback, double calificacion, 
			String comentario, Date fecha, String feedbackID ) {
		// Envia un feedback al profesor del Learning Path
        Feedback feedbackEstudiante= new Feedback(feedbackID, comentario, calificacion, fecha, this, learningPath);
        learningPath.getFeedback().add(feedbackEstudiante);
    
		
	}
	
	public Progreso crearProgreso(LearningPath learningPath, Date fechaInicio, Date fechaCompletado, int tiempoDedicado,
			double estado) {
		// Crea un registro de progreso para una actividad
		int random = (int) (Math.random() * 1000 + 1);
		String randomString = Integer.toString(random);
		Progreso progreso = new Progreso(randomString, this.usuarioID, learningPath, fechaInicio, fechaCompletado, tiempoDedicado, estado);
		return progreso;
	}
	
	
	
	
	
}
