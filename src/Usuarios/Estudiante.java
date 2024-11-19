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
	
	public Map<String,LearningPath> getLearningPathsInscritos() {
		return learningPathsInscritos;
	}
	
	public String obtenerRecomendacion(String intereses, String profesorID) {
		
		Profesor profesor = profesores.get(profesorID);
		List<LearningPath> learningPaths = profesor.getLearningPathsCreados().values().stream().toList();
		for (LearningPath lp : learningPaths) {
		}
		List<String> recomendaciones = new ArrayList<>();
		for (LearningPath lp : learningPaths) {
			if (lp.getIntereses().contains(intereses)) {
				recomendaciones.add(lp.getTitulo());
			}
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
			System.out.println("ActividadID: " + actividadID);
			Actividad actividad = learningPath.actividades.get(actividadID);
			System.out.println("Actividad: " + actividad);
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
        Progreso progreso2 = crearProgreso(learningPath, new Date(), new Date(), 0, "En Progreso");
        progresoLearningPath.put(learningPath, progreso2);
        learningPath.progresoEstudiante.put(this.usuarioID, progreso2);

        Map<String, String> map = new HashMap<>();
        map.put("Titulo Learning Path", learningPathEstudiante.getTitulo());
        map.put("Descripcion learning Path", learningPathEstudiante.getDescripcion());

        for (String actividadID : actividades) {
        	
            Actividad actividad = learningPathEstudiante.actividades.get(actividadID);
            System.out.println("Actividad: " + actividad.getActividadID());
            this.actividades.put(actividadID, actividad);
            map.put("Actividad " + actividad.getActividadID(), actividad.getDescripcion());
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
	public Actividad clonarActividad(String tipoActividad, Actividad actividad, String estudianteID) {
	    Actividad actividadEstudiante = null;

	    if (tipoActividad.equals("Quiz")) {
	        Quiz quiz = (Quiz) actividad;
	        List<String> actividadesPrevias = new ArrayList<>();
	        for (String actividadPrevia : quiz.getActividadesPrevias()) {
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
	    } else if (tipoActividad.equals("Examen")) {
	        Examen examen = (Examen) actividad;
	        List<String> actividadesPrevias = new ArrayList<>();
	        for (String actividadPrevia : examen.getActividadesPrevias()) {
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
	    } else if (tipoActividad.equals("Encuesta")) {
	        Encuesta encuesta = (Encuesta) actividad;
	        List<String> actividadesPrevias = new ArrayList<>();
	        for (String actividadPrevia : encuesta.getActividadesPrevias()) {
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
	    } else if (tipoActividad.equals("Recurso Educativo")) {
	        RecursoEducativo recurso = (RecursoEducativo) actividad;
	        List<String> actividadesPrevias = new ArrayList<>();
	        for (String actividadPrevia : recurso.getActividadesPrevias()) {
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
	    } else if (tipoActividad.equals("Tarea")) {
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
		
	

	public Actividad completarActividad(String actividadID, String learningPathID) throws ActividadNoPertenece, YaSeCompleto {

		String idLpEstudiante = learningPathID + "_" + this.usuarioID;
		String actividadIDEstudiante = actividadID + "_" + this.usuarioID;
        
        LearningPath learningPath = learningPathsInscritos.get(idLpEstudiante);
         
        if (learningPath == null || !learningPath.getActividades().containsKey(actividadIDEstudiante)) {
            throw new ActividadNoPertenece("La actividad no pertenece al learning path");
        }
		if (learningPath.getActividades().get(actividadIDEstudiante).getEstado() == "Exitoso") {
			throw new YaSeCompleto("La actividad ya ha sido completada");
		}
        Map<String, Actividad> mapa = learningPath.getActividades();
        Actividad actividad = mapa.get(actividadIDEstudiante);
        List<String> actividadesPrevias = actividad.getActividadesPrevias();
        ArrayList<String> actividadesPrevias2 = new ArrayList<String>();
        
        for (String actividadPrevia : actividadesPrevias) {
        	Actividad act = mapa.get(actividadPrevia);
            if (act.getEstado()== null ) {
            	actividadesPrevias2.add(act.getActividadID());
                
            }
        }
        System.out.println("Tenga cuidado no ha realizado las actividades previas: " + actividadesPrevias2);

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
        
		return actividad;
    }
	
	
	
		
	
	 public double getProgresoLearningPath(String learningPathID) throws LearningPathNoInscrito {
	        LearningPath learningPath = learningPathsInscritos.get(learningPathID);
	        if (learningPath == null) {
                throw new LearningPathNoInscrito("Learning Path no inscrito");
                }
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
			String feedbackID ) throws LearningPathNoInscrito{
		LearningPath lp= learningPathsInscritos.get(learningPath);
		if(lp==null) {
            throw new LearningPathNoInscrito("Learning Path no inscrito");
            }
		//Imprime la lista de learning paths inscritos
		Feedback feedbackEstudiante= new Feedback(feedbackID, feedback, calificacion, this.getNombre(), lp);
		lp.getFeedback().add(feedbackEstudiante);
	}
	
	public Progreso crearProgreso(LearningPath learningPath, Date fechaInicio, Date fechaCompletado, int tiempoDedicado,
			String estado) {
		// Crea un registro de progreso para una actividad
		int random = (int) (Math.random() * 1000 + 1);
		String randomString = Integer.toString(random);
		Progreso progreso = new Progreso(randomString, this.usuarioID, learningPath, fechaInicio, fechaCompletado, tiempoDedicado, estado);
		return progreso;
	}
	
	
	public void inscribirProfesor(Profesor profesor, String profesorID) {
		//Agregar al mapa del estudiantes el profesor
		profesores.put(profesorID, profesor);
		
	}
	
	
	
}
