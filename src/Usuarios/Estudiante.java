package Usuarios;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
	public String estudiante = "Estudiante";
	public HashMap<String,Profesor> profesores = new HashMap<>();
	public Map<LearningPath, Progreso> progresoLearningPath = new HashMap<>();
	private HashMap<String, Actividad> actividades = new HashMap<>();
	private String intereses;
	
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
	
	public String getIntereses() {
        return intereses;
    }
	
	public void setIntereses(String intereses) {
		this.intereses = intereses;
	}
	
	public String obtenerRecomendacion(String intereses, String profesorID) {
		
		Profesor profesor = profesores.get(profesorID);
		List<LearningPath> learningPaths = profesor.getLearningPathsCreados().values().stream().toList();
		List<String> recomendaciones = new ArrayList<>();
		for (LearningPath lp : learningPaths) {
			if (lp.getIntereses().contains(intereses)) {
				recomendaciones.add(lp.getTitulo());
			}
		}
		return recomendaciones.toString();
	}
	
	
	public Map<String, String > inscribirLearningPath(String LearningPathID, String profesorID) {
		
		Profesor profesor = profesores.get(profesorID);
		LearningPath learningPath = profesor.getLearningPathsCreados().get(LearningPathID);
		learningPathsInscritos.put(LearningPathID,learningPath);
		learningPath.estudiantesInscritos.put(usuarioID, this);
		List<String> actividades = learningPath.getActividadesID();
		Progreso progreso = crearProgreso(learningPath, new Date(), new Date(), 0, "En Progreso");
		progresoLearningPath.put(learningPath, progreso);
		Map<String, String > map = new HashMap<>();
			map.put("Titulo Learning Path", learningPath.getTitulo());
			map.put("Descripcion learning Path", learningPath.getDescripcion());
			for (String actividadID : actividades) { 
				Actividad actividad = learningPath.actividades.get(actividadID);
				this.actividades.put(actividadID, actividad);
					map.put("Actividad "+actividad.getActividadID(), actividad.getDescripcion());
				}
		
		return map;
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
        
        Actividad actividadPrevia = learningPath.getActividades().get(actividad.getActividadPrevia());
       	actividad.setFechaLimite(actividadPrevia);
        LocalDateTime LocaldateTimeNow = LocalDateTime.now();
        Date fechaInicio = Date.from(LocaldateTimeNow.atZone(ZoneId.systemDefault()).toInstant());
        actividad.setFechainicio(fechaInicio);
        
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
        
        LocalDateTime LocaldateTimeNow2 = LocalDateTime.now();
        Date fechaFin = Date.from(LocaldateTimeNow2.atZone(ZoneId.systemDefault()).toInstant());
        actividad.setFechafin(fechaFin);
        
        
        List<String> actividadSeguimiento =  actividad.getActividadesSeguimiento();
        Progreso progreso = progresoLearningPath.get(learningPath);
        List<Actividad> actividades = learningPath.actividades.values().stream().toList();
        learningPath.actualizarProgreso(progreso, actividades);
        learningPath.getProgresoEstudiante().put(this.usuarioID, progreso);
        
        System.out.println("Se le recomienda realizar la actividad: " + actividadSeguimiento);
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
			if (actividadesDisponibles.isEmpty()) {
		        System.out.println("No hay actividades disponibles en el Learning Path: " + learningPathID);
		        Progreso progreso = progresoLearningPath.get(learningPath);
		        if (progreso != null) {
		            progreso.setEstado("Completado");
		        } else {
		            System.out.println("Progreso not found for Learning Path: " + learningPathID);
		        }
		    }
			
			return actividadesDisponibles;
		}

	 
	public Feedback enviarFeedback(String learningPath, String feedback, int calificacion, 
 String feedbackID ) {
		// Envia un feedback al profesor del Learning Path
		LearningPath lp= learningPathsInscritos.get(learningPath);
        Feedback feedbackEstudiante= new Feedback(feedbackID, feedback, calificacion, this.getNombre(), lp);
        lp.getFeedback().add(feedbackEstudiante);
        return feedbackEstudiante;
		
	}
	
	public Progreso crearProgreso(LearningPath learningPath, Date fechaInicio, Date fechaCompletado, int tiempoDedicado,
			String estado) {
		// Crea un registro de progreso para una actividad
		int random = (int) (Math.random() * 1000 + 1);
		String randomString = Integer.toString(random);
		Progreso progreso = new Progreso(randomString, this.usuarioID, learningPath, fechaInicio, fechaCompletado, tiempoDedicado, estado);
		return progreso;
	}
	
	
	
	
	
}
