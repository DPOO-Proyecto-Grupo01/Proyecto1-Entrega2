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
import Exceptions.ActividadNoPertenece;
import Exceptions.LearningPathNoInscrito;
import Exceptions.YaSeCompleto;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaFeedback;
import Perisistencia.PersistenciaLearningPaths;
import Perisistencia.PersistenciaProgreso;

public class Estudiante extends Usuario {
	public Map<String,LearningPath> learningPathsInscritos = new HashMap<>();
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
	
	public Map<String,LearningPath> getLearningPathsInscritos() {
		return learningPathsInscritos;
	}
	
	public String obtenerRecomendacion(String intereses, String profesorID) {
		
		Profesor profesor = profesores.get(profesorID);
		List<LearningPath> learningPaths = profesor.getLearningPathsCreados().values().stream().toList();
		
		List<String> recomendaciones = new ArrayList<>();
		for (LearningPath lp : learningPaths) {
				recomendaciones.add(lp.getTitulo());
		}
		return recomendaciones.toString();
	}
	
	

public Map<String, String> inscribirLearningPath(String LearningPathID, String profesorID) throws LearningPathNoInscrito {
    try {
        Profesor profesor = profesores.get(profesorID);
        if (profesor == null) {
            throw new LearningPathNoInscrito("Profesor no encontrado: " + profesorID);
        }
        
        LearningPath learningPath = profesor.getLearningPathsCreados().get(LearningPathID);
        if (learningPath == null) {
            throw new LearningPathNoInscrito("Learning Path no encontrado: " + LearningPathID);
        }
        
        LearningPath learningPathEstudiante = clonarLearningPath(learningPath, this.usuarioID);
        Map<String,Actividad> actividadesLPE= new HashMap<>();

		for (String actividadID : learningPath.getActividadesID()) {

			Actividad actividad = learningPath.actividades.get(actividadID);

			Actividad actividadEstudiante = clonarActividad(actividad.getTipoActividad(), actividad, this.usuarioID);
			actividadEstudiante.setActividadPrevia(actividad.getActividadPrevia()+"_"+this.usuarioID);
			actividadesLPE.put(actividadEstudiante.getActividadID(), actividadEstudiante);
			
		}
		learningPathEstudiante.setActividades(actividadesLPE);
		learningPathsInscritos.put(learningPath.getLearningPathID(), learningPath);
        learningPathsInscritos.put(learningPathEstudiante.getLearningPathID(), learningPathEstudiante);
        learningPath.estudiantesInscritos.put(usuarioID, this);

        List<String> actividades = learningPathEstudiante.getActividadesID();
        Progreso progreso = crearProgreso(learningPathEstudiante, new Date(), new Date(), 0, "En Progreso");
        progresoLearningPath.put(learningPathEstudiante, progreso);
        learningPathEstudiante.progresoEstudiante.put(this.usuarioID, progreso);
        learningPathEstudiante.setProgreso(progreso);

        Map<String, String> map = new HashMap<>();
        map.put("Titulo Learning Path", learningPathEstudiante.getTitulo());
        map.put("Descripcion learning Path", learningPathEstudiante.getDescripcion());

        for (String actividadID : actividades) {
        	
            Actividad actividad = learningPathEstudiante.actividades.get(actividadID);
            
            this.actividades.put(actividadID, actividad);
            map.put("Actividad " + actividad.getActividadID(), actividad.getDescripcion());
        }
        
        PersistenciaLearningPaths persistenciaLearningPaths = new PersistenciaLearningPaths();
        PersistenciaActividades persistenciaActividades = new PersistenciaActividades();
        PersistenciaProgreso persistenciaProgreso = new PersistenciaProgreso();
        
        try {
        persistenciaLearningPaths.actualizarLearningPathEstudiante(learningPathEstudiante);
        persistenciaLearningPaths.actualizarLearningPathEstudiante(learningPath);
        persistenciaProgreso.actualizarProgreso(progreso);
		for (Actividad actividad : actividadesLPE.values()) {
			persistenciaActividades.actualizarActividadesEstudiante(actividad);
		}
	} catch (Exception e) {
		e.printStackTrace();
        }
        return map;

    } catch (LearningPathNoInscrito e) {
        throw e;   
    } 
}

	public LearningPath clonarLearningPath(LearningPath lp, String estudianteID) {
		List<String>actividadesOriginales=lp.getActividadesID();
		List<String>actividadesClonadas=new ArrayList<>();
		Map<String, Actividad> actividadesMap = new HashMap<>();
		
		for (String actividad : actividadesOriginales) {
            String actividadClonada = actividad+"_"+estudianteID;
            actividadesClonadas.add(actividadClonada);
        }
		LearningPath lpEstudiante = new LearningPath(lp.getLearningPathID()+"_"+estudianteID, lp.getTitulo(), lp.getDescripcion(), lp.getObjetivos(), lp.getNivelDificultad(), lp.getDuracionMinutos(), lp.getProfesorID(), actividadesClonadas, lp.getIntereses());
		lp.inscribirEstudiante(estudianteID, lpEstudiante);
		
		for (Actividad actividad : lp.getActividades().values()) {
			
			Actividad actividadClonada = clonarActividad(actividad.getTipoActividad(), actividad, estudianteID);
			actividadesMap.put(actividadClonada.getActividadID(), actividadClonada);
		}
		
		lpEstudiante.setActividades(actividadesMap);
		return lpEstudiante;
		
	}
	
	//Metodo para clonar actividades
	@SuppressWarnings("null")
	public Actividad clonarActividad(String tipoActividad, Actividad actividad, String estudianteID) {
	    Actividad actividadEstudiante = null;

	    if (tipoActividad.equals("Quiz")) {
	        Quiz quiz = (Quiz) actividad;
	        List<String> actividadesPrevias = new ArrayList<>();
	        for (String actividadPrevia : quiz.getActividadesPrevias()) {
	        	if (!(actividadPrevia == null)|| !(actividadPrevia.equals(""))) {
                actividadesPrevias.add(actividadPrevia + "_" + estudianteID);
                }
	        actividadEstudiante = new Quiz(
	            quiz.getActividadID() + "_" + estudianteID,
	            quiz.getDescripcion(),
	            quiz.getObjetivo(),
	            quiz.getNivelDificultad(),
	            quiz.getDuracionEsperada(),
	            quiz.isEsObligatoria(),
	            quiz.getFechaLimite(),
	            quiz.getResenas(),
	            quiz.getResultado(),
	            quiz.getCalificacion(),
	            actividadesPrevias,
	            quiz.getActividadesSeguimiento(),
	            quiz.getPreguntas(),
	            quiz.getCalificacionMinima(),
	            quiz.getRespuestaCorrecta()
	        );
	    }} else if (tipoActividad.equals("Examen")) {
	        Examen examen = (Examen) actividad;
	        List<String> actividadesPrevias = new ArrayList<>();
	        
	        for (String actividadPrevia : examen.getActividadesPrevias()) {
	        	if (!(actividadPrevia == null)|| !(actividadPrevia.equals(""))) {
                actividadesPrevias.add(actividadPrevia + "_" + estudianteID);
                	}
	        
	        actividadEstudiante = new Examen(
	            examen.getActividadID() + "_" + estudianteID,
	            examen.getDescripcion(),
	            examen.getObjetivo(),
	            examen.getNivelDificultad(),
	            examen.getDuracionEsperada(),
	            examen.isEsObligatoria(),
	            examen.getFechaLimite(),
	            examen.getResenas(),
	            examen.getResultado(),
	            examen.getCalificacion(),
	            actividadesPrevias,
	            examen.getActividadesSeguimiento(),
	            examen.getPreguntas(),
	            examen.getCalificacionMinima()
	        );
	        } } else if (tipoActividad.equals("Encuesta")) {
	        Encuesta encuesta = (Encuesta) actividad;
	        List<String> actividadesPrevias = new ArrayList<>();
	        for (String actividadPrevia : encuesta.getActividadesPrevias()) {
	        if (!(actividadPrevia == null)|| !(actividadPrevia.equals(""))) {
                actividadesPrevias.add(actividadPrevia + "_" + estudianteID);
                }
	        actividadEstudiante = new Encuesta(
	            encuesta.getActividadID() + "_" + estudianteID,
	            encuesta.getDescripcion(),
	            encuesta.getObjetivo(),
	            encuesta.getNivelDificultad(),
	            encuesta.getDuracionEsperada(),
	            encuesta.isEsObligatoria(),
	            encuesta.getFechaLimite(),
	            encuesta.getResenas(),
	            encuesta.getResultado(),
	            encuesta.getCalificacion(),
	            encuesta.getActividadesPrevias(),
	            encuesta.getActividadesSeguimiento(),
	            encuesta.getPreguntas()
	        );
	        }} else if (tipoActividad.equals("Recurso Educativo")) {
	        RecursoEducativo recurso = (RecursoEducativo) actividad;
	        List<String> actividadesPrevias = new ArrayList<>();
	        for (String actividadPrevia : recurso.getActividadesPrevias()) {
	        	if (!(actividadPrevia == null)|| !(actividadPrevia.equals(""))) {
				actividadesPrevias.add(actividadPrevia + "_" + estudianteID);
			}
	        actividadEstudiante = new RecursoEducativo(
	            recurso.getActividadID() + "_" + estudianteID,
	            recurso.getDescripcion(),
	            recurso.getObjetivo(),
	            recurso.getNivelDificultad(),
	            recurso.getDuracionEsperada(),
	            recurso.isEsObligatoria(),
	            recurso.getFechaLimite(),
	            recurso.getResenas(),
	            recurso.getResultado(),
	            recurso.getCalificacion(),
	            actividadesPrevias,
	            recurso.getActividadesSeguimiento(),
	            recurso.getTipoRecurso(),
	            recurso.getLinkRecusro()
	        );
	    } } else if (tipoActividad.equals("Tarea")) {
	        Tarea tarea = (Tarea) actividad;
	        List<String> actividadesPrevias = new ArrayList<>();
	        for (String actividadPrevia : tarea.getActividadesPrevias()) {
	        	actividadesPrevias.add(actividadPrevia + "_" + estudianteID);
	        }
	        actividadEstudiante = new Tarea(
	            tarea.getActividadID() + "_" + estudianteID,
	            tarea.getDescripcion(),
	            tarea.getObjetivo(),
	            tarea.getNivelDificultad(),
	            tarea.getDuracionEsperada(),
	            tarea.isEsObligatoria(),
	            tarea.getFechaLimite(),
	            tarea.getResenas(),
	            tarea.getResultado(),
	            tarea.getCalificacion(),
	            actividadesPrevias,
	            tarea.getActividadesSeguimiento(),
	            tarea.getPreguntas(),
	            tarea.getInstrucciones(),
	            tarea.getEstado()
	        );
	    }

	    return actividadEstudiante;
	} 
		
	

	public Actividad completarActividad(String actividadID, String learningPathID) throws Exception {

		String idLpEstudiante = learningPathID + "_" + this.usuarioID;
		String actividadIDEstudiante = actividadID + "_" + this.usuarioID;
        
        LearningPath learningPath = learningPathsInscritos.get(idLpEstudiante);
         
        if (learningPath == null || !learningPath.getActividades().containsKey(actividadIDEstudiante)) {
            throw new ActividadNoPertenece("La actividad no pertenece al learning path");
        }
		if (learningPath.getActividades().get(actividadIDEstudiante).getEstado()==("Exitoso")) {
			throw new YaSeCompleto("La actividad ya ha sido completada");
		}
        Map<String, Actividad> mapa = learningPath.getActividades();
        Actividad actividad = mapa.get(actividadIDEstudiante);
        List<String> actividadesPrevias = actividad.getActividadesPrevias();
        ArrayList<String> actividadesPrevias2 = new ArrayList<String>();
        
        for (String actividadPrevia : actividadesPrevias) {
			if (actividadPrevia == null||actividadPrevia.equals("")) {
			}
			else{
        	Actividad act = mapa.get(actividadPrevia);
            if (act.getEstado()== null ) {
            	actividadesPrevias2.add(act.getActividadID());
                
            }}
        }


        Actividad actividadPrevia = learningPath.getActividades().get(actividad.getActividadPrevia());
        
        
        LocalDateTime LocaldateTimeNow = LocalDateTime.now();
        Date fechaInicio = Date.from(LocaldateTimeNow.atZone(ZoneId.systemDefault()).toInstant());
        actividad.setFechainicio(fechaInicio);
        actividad.setFechaLimite(actividad);
        
        if (actividad.getTipoActividad().equals("Tarea")) {
            Tarea tarea = (Tarea) actividad;
            tarea.completarTarea();
            tarea.setEstado("Enviado");
        } else if (actividad.getTipoActividad().equals("Examen")) {
            Examen examen = (Examen) actividad;
            examen.completarExamen();
            examen.setEstado("Exitoso");
        } else if (actividad.getTipoActividad().equals("RecursoEducativo")) {
            RecursoEducativo recurso = (RecursoEducativo) actividad;
            recurso.completarRecurso();
            recurso.setEstado("Enviado");
        } else if (actividad.getTipoActividad() .equals("Quiz")) {
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
        Progreso progreso = learningPath.getProgreso();
        List<Actividad> actividades = learningPath.actividades.values().stream().toList();
        learningPath.actualizarProgreso(progreso, actividades);
        learningPath.getProgresoEstudiante().put(this.usuarioID, progreso);
        
        System.out.println("Se le recomienda realizar la actividad: " + actividadSeguimiento);
        
		return actividad;
    }
	
	
	
	 public double getProgresoLearningPath(String learningPathID) throws LearningPathNoInscrito {
	        LearningPath learningPath = learningPathsInscritos.get(learningPathID+ "_" + this.usuarioID);
	        if (learningPath == null) {
                throw new LearningPathNoInscrito("Learning Path no inscrito");
                }
	        Progreso progreso = learningPath.getProgreso();
	        
	        if (progreso != null) {
	            return progreso.getPorcentajeDeExito();
	            
	        } else {
	            return 10000.000;
	        }
	    }
	 
	 public List<Actividad> actividadesDisponibles(String learningPathID) {
			// Muestra las actividades disponibles en los Learning Paths inscritos.
			List<String> actividades = learningPathsInscritos.get(learningPathID).getActividadesID();
			System.out.println("Actividades disponibles en el Learning Path: " + actividades);
			
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

	 

	public void enviarFeedback(String learningPath, String feedback, int calificacion, 
			String feedbackID ) throws Exception{
		LearningPath lp= learningPathsInscritos.get(learningPath);
		if(lp==null) {
            throw new LearningPathNoInscrito("Learning Path no inscrito");
            }
		//Imprime la lista de learning paths inscritos
		Feedback feedbackEstudiante= new Feedback(feedbackID, feedback, calificacion, this.getUsuarioID(), learningPath);
		PersistenciaFeedback persistenciaFeedback = new PersistenciaFeedback();
	    persistenciaFeedback.actualizarFeedback(feedbackEstudiante);
		List<Feedback> feedbacks= lp.getFeedback();
		feedbacks.add(feedbackEstudiante);
		lp.setFeedback(feedbacks);
	}
	
	public Progreso crearProgreso(LearningPath learningPath, Date fechaInicio, Date fechaCompletado, int tiempoDedicado,
			String estado) {
		// Crea un registro de progreso para una actividad
		String ID = "progreso_" +learningPath.getLearningPathID();
		Progreso progreso = new Progreso(ID, this.usuarioID, learningPath.getLearningPathID(), fechaInicio, fechaCompletado, tiempoDedicado, estado,0.0);
		return progreso;
	}
	
	
	public void inscribirProfesor(Profesor profesor, String profesorID) {
		//Agregar al mapa del estudiantes el profesor
		profesores.put(profesorID, profesor);
		
	}
	
	
	public ArrayList<String> actividadesSinCompletar (Actividad actividad, LearningPath learningPath) {
        Map<String, Actividad> mapa = learningPath.getActividades();
        List<String> actividadesPrevias = actividad.getActividadesPrevias();
        ArrayList<String> actividadesPrevias2 = new ArrayList<String>();
        
        ArrayList<String> res = new ArrayList<String>();
        for (String actividadPrevia : actividadesPrevias) {
			if (actividadPrevia == null||actividadPrevia.equals("")) {
				res= null;
			}
			
			else{
        	Actividad act = mapa.get(actividadPrevia);
            if (act.getEstado()== null ) {
            	actividadesPrevias2.add(act.getActividadID());
            	res= actividadesPrevias2;
            	
                
            }}
		
        }
		return res;
		
	}
	
	
}
