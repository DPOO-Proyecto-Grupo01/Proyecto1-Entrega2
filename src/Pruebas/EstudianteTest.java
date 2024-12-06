package Pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.net.Authenticator;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Actividades.*;
import Exceptions.*;
import LearningPaths.*;
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaLearningPaths;
import Perisistencia.PersistenciaUsuarios;
import Usuarios.*;

class EstudianteTest {
	

	private Estudiante estudiantePrueba;
	public static List<LearningPath> learningPaths;
	public List<Feedback> feedback;
	public List<Progreso> progreso;
	public static List<Estudiante> estudiantes;
	public static List<Profesor> profesores;
	public static List<Actividad> actividades;
	private LearningPath learningPath;
	
	private Authenticator authentication;
	private static PersistenciaUsuarios persistenciaUsuarios;
	private static PersistenciaLearningPaths persistenciaLearningPaths;
	private static PersistenciaActividades persistenciaActividades;
	private static final String actividadesFile = "src\\datos\\activities.json";
	private static final String learningPathsFile = "src\\datos\\learning_paths.json";
	private static final String usuarios = "src\\datos\\users.json";


	@BeforeEach
	public void setUp() throws Exception {
		
		// Iniciando sesión, por favor espere...
		persistenciaActividades = new PersistenciaActividades();
		persistenciaUsuarios = new PersistenciaUsuarios();
		persistenciaLearningPaths = new PersistenciaLearningPaths();

		try {
		    // Cargar datos desde los archivos
		    estudiantes = persistenciaUsuarios.cargarEstudiantes(usuarios);
		    profesores = persistenciaUsuarios.cargarProfesores(usuarios);
		    learningPaths = persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
		    actividades = persistenciaActividades.cargarActividades(actividadesFile);
		    persistenciaLearningPaths.cargarActividadesDelLearningPath(actividades, learningPaths);
		    
		    // Probar persistencia de quiz
		    ArrayList<String> actividadesID = new ArrayList<>();
		    actividadesID.add("A101");
		    actividadesID.add("A103");
		    actividadesID.add("A102");
		    actividadesID.add("A110");
		    actividadesID.add("A204");
		    actividadesID.add("A203");
		    

		    ArrayList<String> intereses = new ArrayList<>();
		    intereses.add("Java");
		    intereses.add("Programacion");

		    if (!actividades.contains(actividades.get(1))) {
		        persistenciaActividades.salvarActividad("actividades.json", actividades.get(1));
		    }
		    
		    Estudiante estudiante = new Estudiante("U105", "Juan Perez", "1234", "J.perez@uniandes.edu.co",
		            "Estudiante");
		
		    Profesor profesor = new Profesor("P105", "Carlos Perez", "1234", "C.perez@uniandes.edu.co", "Profesor");


		    if (!estudiantes.contains(estudiante)) {
		        estudiantes.add(estudiante);
		        persistenciaUsuarios.salvarEstudiante(usuarios, estudiante.getUsuarioID(), estudiante.getNombre(),
		                estudiante.getContraseña(), estudiante.getEmail(), estudiante.getTipoUsuario());
		    }
		    if (!profesores.contains(profesor)) {
		        profesores.add(profesor);
		        persistenciaUsuarios.salvarProfesor(usuarios, profesor.getUsuarioID(), profesor.getNombre(),
		                profesor.getContraseña(), profesor.getEmail(), profesor.getTipoUsuario());
		    }

		    estudiante.profesores.put("P105", profesor);

		    LearningPath createdLearningPath = profesor.crearLearningPath("LP106", "Aprendiendo a programar en Java",
		            "Descripcion", "Objetivos", 3, 120, "P105", actividadesID, intereses);
		    if (!learningPaths.contains(createdLearningPath)) {
		        learningPaths.add(createdLearningPath);
		        persistenciaLearningPaths.salvarLearningPath(learningPathsFile, createdLearningPath.getLearningPathID(),
		                createdLearningPath.getTitulo(), createdLearningPath.getDescripcion(),
		                createdLearningPath.getObjetivos(), createdLearningPath.getNivelDificultad(),
		                createdLearningPath.getDuracionMinutos(), createdLearningPath.getProfesorID(),
		                createdLearningPath.getActividadesID(), createdLearningPath.getIntereses());
		    } 
		    createdLearningPath.actividades.put("A101", actividades.get(0));
		    createdLearningPath.actividades.put("A103", actividades.get(2));
		    createdLearningPath.actividades.put("A102", actividades.get(1));
		    createdLearningPath.actividades.put("A110", actividades.get(3));
		    createdLearningPath.actividades.put("A203", actividades.get(4));
		    createdLearningPath.actividades.put("A204", actividades.get(5));

		    ArrayList<String> actividadesPrevias = new ArrayList<>();
		    actividadesPrevias.add("A101");
		    actividadesPrevias.add("A103");

		    ArrayList<String> actividadesSeguimiento = new ArrayList<>();
		    actividadesSeguimiento.add("A102");

		    ArrayList<Pregunta> preguntas = new ArrayList<>();
		    ArrayList<String> respuestas = new ArrayList<>();
		    respuestas.add("A");
		    respuestas.add("B");
		    respuestas.add("C");
		    HashMap<String, Object> atributosEspecificos = new HashMap<>();
		    atributosEspecificos.put("calificacionMinima", 0.5);
		    atributosEspecificos.put("RespuestaCorrecta", respuestas.get(0));

		    preguntas.add(new Pregunta("Para que es public, private, protected", respuestas));
		    atributosEspecificos.put("Preguntas", preguntas);

		    String fecha = "2021-12-01";
		    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		    Actividad actividadCreada = (Quiz) profesor.crearActividad("A110", "Descripcion", "Objetivos", 3, 120, true,
		            date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento,
		            atributosEspecificos, "A103");

		    
		    
		    Actividad actividadCreada1 = profesor.crearActividad("A101", "Descripcion", "Objetivos", 3, 120, true, date,
					"reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,
					"A103");
			Actividad actividadCreada2 = profesor.crearActividad("A103", "Descripcion", "Objetivos", 3, 120, true, date,
					"reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,
					"A110");
			Actividad actividadCreada3 = profesor.crearActividad("A102", "Descripcion", "Objetivos", 3, 120, true, date,
					"reseña", 0, 0, "Tarea", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,
					"A103");

		    profesor.CalificacionMinima("A110", 60.1);

		    estudiante.inscribirLearningPath("LP106", "P105");

		    profesor.revisarEstadoActividad("A110", "LP106");

		    Actividad quiz = estudiante.completarActividad("A103", "LP106");

		    ArrayList<String> actividadesID1 = new ArrayList<>();
		    for (Actividad actividad : estudiante.actividadesDisponibles("LP106_U105")) {
		        actividadesID1.add(actividad.getActividadID());
		    }

		    estudiante.completarActividad("A101", "LP106");
		    ArrayList<String> actividadesID2 = new ArrayList<>();
		    for (Actividad actividad : estudiante.actividadesDisponibles("LP106_U105")) {
		        actividadesID2.add(actividad.getActividadID());
		       
            }
		    estudiantePrueba = estudiante;
		    learningPath = createdLearningPath;
		    
		    Examen examen = new Examen("A102", "Examen Descripcion", "Examen Objetivo", 3, 120, true, new Date(), "reseña", 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0.5);
		    Encuesta encuesta = new Encuesta("A103", "Encuesta Descripcion", "Encuesta Objetivo", 3, 120, true, new Date(), "reseña", 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
		    RecursoEducativo recurso = new RecursoEducativo("A104", "Recurso Descripcion", "Recurso Objetivo", 3, 120, true, new Date(), "reseña", 0, 0, new ArrayList<>(), new ArrayList<>(), "Video", "http://recurso.com");
		    Tarea tarea = new Tarea("A105", "Tarea Descripcion", "Tarea Objetivo", 3, 120, true, new Date(), "reseña", 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), "Instrucciones", "Pendiente");
		    
		    createdLearningPath.actividades.put("A105", tarea);
		    
	
		    learningPath.getActividades().put("A102", examen);
		    learningPath.getActividades().put("A103", encuesta);
		    learningPath.getActividades().put("A104", recurso);
		    learningPath.getActividades().put("A105", tarea);
		    learningPath.actividades.put("A104", recurso);
		    learningPath.actividades.put("A105", tarea);
		

		    estudiantePrueba.inscribirLearningPath("LP106", "P105");
		    
		}
		
		catch (Exception e) {
		    e.printStackTrace();
		}
	}

		    

    
    @Test
    void testInscribirLearningPath() throws LearningPathNoInscrito {
    	estudiantePrueba.inscribirLearningPath("LP106", "P105");
        
        Map<String, LearningPath> learningPaths = estudiantePrueba.getLearningPathsInscritos();
        
        String expectedLearningPathID = "LP106_" + estudiantePrueba.getUsuarioID();
        
        assertTrue(learningPaths.containsKey(expectedLearningPathID));
    }


    @Test
    void testInscribirLearningPathInvalido() {
        assertThrows(LearningPathNoInscrito.class, () -> {
        	estudiantePrueba.inscribirLearningPath("LP999", "P105");
        });
    }

    @Test
    void testCompletarActividad() throws Exception {
        // Ensure activity is added to the learning path
        Quiz actividad = new Quiz("A101", "Descripcion", "Objetivo", 3, 120, true, new Date(), "reseña", 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0.5, "A");
        learningPath.getActividades().put("A101", actividad);
        
        estudiantePrueba.inscribirLearningPath("LP106", "P105");

        Actividad resultado = estudiantePrueba.completarActividad("A101", "LP106");
        assertNotNull(resultado, "Activity result should not be null");
        assertEquals("Exitoso", resultado.getEstado());
    }

    /// Test completar Tarea
   
    
    /// Test completar Encuesta
    @Test
    void testCompletarEncuestaActividad() throws Exception {
        Actividad resultado = estudiantePrueba.completarActividad("A103", "LP106");
        assertNotNull(resultado, "Activity result should not be null");
        assertEquals("Exitoso", resultado.getEstado());
    }
    
    /// Test completar Examen
    @Test
    void testCompletarExamenActividad() throws Exception {
        Actividad resultado = estudiantePrueba.completarActividad("A102", "LP106");
        assertNotNull(resultado, "Activity result should not be null");
        assertEquals("Exitoso", resultado.getEstado());
        
    }
    /// Test completar Recurso Educativo
    

    /// Test completar actividad no perteneciente al learning path
    @Test
	void testCompletarActividadNoPertenece() {
		assertThrows(ActividadNoPertenece.class, () -> {
			estudiantePrueba.completarActividad("A111_U105", "LP106_U105");
		});
	}
    /// Test completar actividad ya completada
    @Test 
        void testCompletarActividadYaSeCompleto() {
			assertThrows(YaSeCompleto.class, () -> {
				Actividad actividad = estudiantePrueba.completarActividad("A101", "LP106");
				estudiantePrueba.completarActividad("A101", "LP106");
			});
    }
	@Test
	void testCompletarActividadYaCompletada() throws Exception {
	    Quiz actividad = new Quiz("A101", "Descripcion", "Objetivo", 3, 120, true, new Date(), "reseña", 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0.5, "A");
	    learningPath.getActividades().put("A101", actividad);
	
	    estudiantePrueba.inscribirLearningPath("LP106", "P105");
	
	    Actividad resultado = estudiantePrueba.completarActividad("A101", "LP106");
	    assertNotNull(resultado, "Activity result should not be null");
	    assertEquals("Exitoso", resultado.getEstado());

	}



    @Test
    void testEnviarFeedback() throws LearningPathNoInscrito {
    	estudiantePrueba.inscribirLearningPath("LP106", "P105");
    	estudiantePrueba.enviarFeedback("LP106", "Excelente curso", 5, "FB001");

        Feedback feedback = learningPath.getFeedback().get(0);
        assertEquals("Excelente curso", feedback.getComentario());
        assertEquals(5, feedback.getCalificacion());
    }
    
    /// test de enviar feedback de un learning path no inscrito
    @Test
    void testEnviarFeedbackNoInscrito() {
    	    assertThrows(LearningPathNoInscrito.class, () -> {
        	estudiantePrueba.enviarFeedback("LP999", "Excelente curso", 5, "FB001");
			});
		}

    @Test
    void testObtenerRecomendacion() {
        String recomendacion = estudiantePrueba.obtenerRecomendacion("Java", "P105");
        assertTrue(recomendacion.contains("Aprendiendo a programar en Java"));
    }

    @Test
    void testGetProgresoLearningPath() throws LearningPathNoInscrito {
    	estudiantePrueba.inscribirLearningPath("LP106", "P105");
        double progreso = estudiantePrueba.getProgresoLearningPath("LP106");
        System.out.println(progreso);
        assertEquals(0.0, progreso);
    }
    
  /// test de obtener progreso de un learning path no inscrito
    @Test
    void testGetProgresoLearningPathNoInscrito() {
	    assertThrows(LearningPathNoInscrito.class, () -> {
    	estudiantePrueba.getProgresoLearningPath("LP999_U105");
    	            });
	            }

    @Test
    void testActividadesDisponibles() throws LearningPathNoInscrito {
    	estudiantePrueba.inscribirLearningPath("LP106", "P105");
        Quiz actividad = new Quiz("A101", "Descripcion", "Objetivo", 3, 120, true, new Date(), "reseña", 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), 0.5, "A");
        learningPath.getActividades().put("A101", actividad);

        List<Actividad> actividadesDisponibles = estudiantePrueba.actividadesDisponibles("LP106_U105");
        assertEquals(6, actividadesDisponibles.size(), "There should be one available activity");
        assertEquals("A101_U105", actividadesDisponibles.get(0).getActividadID());
    }
    
    
}

