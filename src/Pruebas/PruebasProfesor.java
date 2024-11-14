package Pruebas;

import static org.junit.jupiter.api.Assertions.*;

import java.net.Authenticator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Actividades.Pregunta;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import Exceptions.NombreRepetido;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaLearningPaths;
import Perisistencia.PersistenciaUsuarios;
import Usuarios.Estudiante;
import Usuarios.Profesor;
import Usuarios.Usuario;


class PruebasProfesor {
	
	private Profesor profesorPrueba;
	public static List<LearningPath> learningPaths;
	public List<Feedback> feedback;
	public List<Progreso> progreso;
	public static List<Estudiante> estudiantes;
	public static List<Profesor> profesores;
	public static List<Actividad> actividades;

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

		    // Probar persistencia de quiz
		    ArrayList<String> actividadesID = new ArrayList<>();
		    actividadesID.add("A101");
		    actividadesID.add("A103");
		    actividadesID.add("A102");
		    actividadesID.add("A110");
		    ArrayList<String> intereses = new ArrayList<>();
		    intereses.add("Java");
		    intereses.add("Programacion");

		    LearningPath newLearningPath = new LearningPath("LP105", "Aprendiendo a programar en Java", "Descripcion",
		            "Objetivos", 3, 120, "P105", actividadesID, intereses);

		    if (!learningPaths.contains(newLearningPath)) {
		        learningPaths.add(newLearningPath);
		        persistenciaLearningPaths.salvarLearningPath(learningPathsFile, newLearningPath.getLearningPathID(),
		                newLearningPath.getTitulo(), newLearningPath.getDescripcion(), newLearningPath.getObjetivos(),
		                newLearningPath.getNivelDificultad(), newLearningPath.getDuracionMinutos(),
		                newLearningPath.getProfesorID(), newLearningPath.getActividadesID(),
		                newLearningPath.getIntereses());
		    }

		    if (!actividades.contains(actividades.get(1))) {
		        persistenciaActividades.salvarActividad("actividades.json", actividades.get(1));
		    }

		    Estudiante estudiante = new Estudiante("U105", "Juan Perez", "1234", "J.perez@uniandes.edu.co",
		            "Estudiante");
		    Estudiante estudiante1 = new Estudiante("U106", "Maria Perez", "1234", "m.perez@uniandes.edu.co",
		            "Estudiante");
		    Profesor profesor = new Profesor("P105", "Carlos Perez", "1234", "C.perez@uniandes.edu.co", "Profesor");
		    profesorPrueba=profesor;

		    if (!estudiantes.contains(estudiante)) {
		        estudiantes.add(estudiante);
		        persistenciaUsuarios.salvarEstudiante(usuarios, estudiante.getUsuarioID(), estudiante.getNombre(),
		                estudiante.getContraseña(), estudiante.getEmail(), estudiante.getTipoUsuario());
		    }
		    if (!estudiantes.contains(estudiante1)) {
		        estudiantes.add(estudiante1);
		        persistenciaUsuarios.salvarEstudiante(usuarios, estudiante1.getUsuarioID(), estudiante1.getNombre(),
		                estudiante1.getContraseña(), estudiante1.getEmail(), estudiante1.getTipoUsuario());
		    }
		    if (!profesores.contains(profesor)) {
		        profesores.add(profesor);
		        persistenciaUsuarios.salvarProfesor(usuarios, profesor.getUsuarioID(), profesor.getNombre(),
		                profesor.getContraseña(), profesor.getEmail(), profesor.getTipoUsuario());
		    }

		    estudiante.profesores.put("P105", profesor);
		    estudiante1.profesores.put("P105", profesor);

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
		    atributosEspecificos.put("preguntas", preguntas);

		    String fecha = "2021-12-01";
		    Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		    Actividad actividadCreada = (Quiz) profesor.crearActividad("A110", "Descripcion", "Objetivos", 3, 120, true,
		            date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento,
		            atributosEspecificos, "A103");

		    if (!actividades.contains(actividadCreada)) {
		        persistenciaActividades.salvarActividad("actividades.json", actividadCreada);
		    }
		    
		    Actividad actividadCreada1 = profesor.crearActividad("A101", "Descripcion", "Objetivos", 3, 120, true, date,
					"reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,
					"A103");
			Actividad actividadCreada2 = profesor.crearActividad("A103", "Descripcion", "Objetivos", 3, 120, true, date,
					"reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,
					"A110");
			Actividad actividadCreada3 = profesor.crearActividad("A102", "Descripcion", "Objetivos", 3, 120, true, date,
					"reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,
					"A103");

		    profesor.CalificacionMinima("A110", 60.1);

		    estudiante.inscribirLearningPath("LP106", "P105");
		    estudiante1.inscribirLearningPath("LP106", "P105");

		    profesor.revisarEstadoActividad("A110", "LP106");

		    Actividad quiz = estudiante.completarActividad("A103_U105", "LP106_U105");
		    estudiante.completarActividad("A110_U105", "LP106_U105");
		    estudiante1.completarActividad("A103_U106", "LP106_U106");

		    ArrayList<String> actividadesID1 = new ArrayList<>();
		    for (Actividad actividad : estudiante.actividadesDisponibles("LP106_U105")) {
		        actividadesID1.add(actividad.getActividadID());
		    }

		    estudiante.completarActividad("A101_U105", "LP106_U105");
		    ArrayList<String> actividadesID2 = new ArrayList<>();
		    for (Actividad actividad : estudiante.actividadesDisponibles("LP106_U105")) {
		        actividadesID2.add(actividad.getActividadID());
		        

		    
		    }
		}
		
		catch (Exception e) {
		    e.printStackTrace();
		}
	}

	
	
	
	/// Test de crear LearningPath
	@Test
	void testCrearLearningPath() throws NombreRepetido {
		List<String> intereses = List.of("programación","dpoo");
		List<String> actividadesID = List.of("A_1","A_2","A_3");
		profesorPrueba.crearLearningPath("LP_1¨", "Como hacer una persistencia", "descripción", "objetivos",3
				, 120 , "P_1", actividadesID, intereses);
		assertEquals(2, profesorPrueba.learningPathsCreados.size());
	}
	
	/// Test de crear learningPath con nombre repetido
	@Test
	void testCrearLearningPathNombreRepetido() throws NombreRepetido {
		List<String> intereses = List.of("programación", "dpoo");
		List<String> actividadesID = List.of("A_1", "A_2", "A_3");
		profesorPrueba.crearLearningPath("LP_1¨", "Como hacer una persistencia", "descripción", "objetivos",3
				, 120 , "P_1", actividadesID, intereses);
		boolean thrown = false;
		try {
			profesorPrueba.crearLearningPath("LP_1¨", "Como hacer una persistencia", "descripción", "objetivos", 3, 120,
					"P_1", actividadesID, intereses);
		} catch (NombreRepetido e) {
			thrown = true;
		}
	}
	
	
	
	/// Test crear Quiz
	@Test
	void testCrearQuiz() throws Exception {
		
		
		persistenciaActividades = new PersistenciaActividades();
		persistenciaUsuarios = new PersistenciaUsuarios();
		persistenciaLearningPaths = new PersistenciaLearningPaths();
		estudiantes = persistenciaUsuarios.cargarEstudiantes(usuarios);
	    profesores = persistenciaUsuarios.cargarProfesores(usuarios);
	    learningPaths = persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
	    actividades = persistenciaActividades.cargarActividades(actividadesFile);
				
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
		actividadesPrevias.add("A101");
		actividadesPrevias.add("A103");
		
		ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
		actividadesSeguimiento.add("A102");
		
		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
		ArrayList<String> respuestas = new ArrayList<String>();
		respuestas.add("A");
		respuestas.add("B");
		respuestas.add("C");
		
		HashMap<String, Object> atributosEspecificos = new HashMap<String, Object >();
		atributosEspecificos.put("calificacionMinima", 0.5);
		atributosEspecificos.put("RespuestaCorrecta", respuestas.get(0));
		
		preguntas.add(new Pregunta("Para que es public, private, protected", respuestas));
		atributosEspecificos.put("preguntas", preguntas);
		
		
		String fecha = "2021-12-01";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		
		Actividad actividadCreada = (Quiz) profesorPrueba.crearActividad("A18", "Descripcion", "Objetivos", 3, 120, true, date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,"A103" );
		assertEquals(5, profesorPrueba.mapaActividades.size());
	}
	
	/// Test crear Tarea
	@Test
	void testCrearTarea() throws ParseException, NombreRepetido {
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
		actividadesPrevias.add("A101");
		actividadesPrevias.add("A103");

		ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
		actividadesSeguimiento.add("A102");

		HashMap<String, Object> atributosEspecificos = new HashMap<String, Object>();
		atributosEspecificos.put("tipoEntrega", "Archivo");
		atributosEspecificos.put("tipoArchivo", "PDF");

		String fecha = "2021-12-01";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		Actividad actividadCreada = (Tarea) profesorPrueba.crearActividad("A18", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Tarea", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		assertEquals(5, profesorPrueba.mapaActividades.size());
	}
	
	/// Test crear Examen
	@Test
	void testCrearExamen() throws ParseException, NombreRepetido {
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
		actividadesPrevias.add("A101");
		actividadesPrevias.add("A103");

		ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
		actividadesSeguimiento.add("A102");

		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();

		HashMap<String, Object> atributosEspecificos = new HashMap<String, Object>();
		atributosEspecificos.put("calificacionMinima", 0.5);

		preguntas.add(new Pregunta("Para que es public, private, protected", null));
		atributosEspecificos.put("preguntas", preguntas);

		String fecha = "2021-12-01";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		Actividad actividadCreada = (Examen) profesorPrueba.crearActividad("A30", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Examen", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		assertEquals(5, profesorPrueba.mapaActividades.size());
	}
	
	/// Test crear Encuesta
	@Test
	void testCrearEncuesta() throws ParseException, NombreRepetido {
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
		actividadesPrevias.add("A101");
		actividadesPrevias.add("A103");

		ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
		actividadesSeguimiento.add("A102");

		ArrayList<String> preguntas = new ArrayList<String>();
		preguntas.add("¿Te gusto la actividad?");

		HashMap<String, Object> atributosEspecificos = new HashMap<String, Object>();
		atributosEspecificos.put("preguntas", preguntas);

		String fecha = "2021-12-01";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		Actividad actividadCreada = (Encuesta) profesorPrueba.crearActividad("A1", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Encuesta", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		assertEquals(5, profesorPrueba.mapaActividades.size());
	}
	
	/// Test crear Recurso Educativo
	@Test
	void testCrearRecursoEducativo() throws ParseException, NombreRepetido {
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
		actividadesPrevias.add("A101");
		actividadesPrevias.add("A103");

		ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
		actividadesSeguimiento.add("A102");

		HashMap<String, Object> atributosEspecificos = new HashMap<String, Object>();
		atributosEspecificos.put("tipoRecurso", "Video");
		atributosEspecificos.put("linkRecurso", "www.youtube.com");

		String fecha = "2021-12-01";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		Actividad actividadCreada = (RecursoEducativo) profesorPrueba.crearActividad("A12", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Recurso Educativo", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		assertEquals(5, profesorPrueba.mapaActividades.size());
	}
	
	/// Test de Calificacion Minima Quiz
	@Test
	void testCalificacionMinima() throws ParseException, NombreRepetido {
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
		actividadesPrevias.add("A101");
		actividadesPrevias.add("A103");
		
		ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
		actividadesSeguimiento.add("A102");
		
		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
		ArrayList<String> respuestas = new ArrayList<String>();
		respuestas.add("A");
		respuestas.add("B");
		respuestas.add("C");
		
		HashMap<String, Object> atributosEspecificos = new HashMap<String, Object >();
		atributosEspecificos.put("calificacionMinima", 0.5);
		atributosEspecificos.put("RespuestaCorrecta", respuestas.get(0));
		
		preguntas.add(new Pregunta("Para que es public, private, protected", respuestas));
		atributosEspecificos.put("preguntas", preguntas);
		
		
		String fecha = "2021-12-01";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		
		Actividad actividadCreada = (Quiz) profesorPrueba.crearActividad("A13", "Descripcion", "Objetivos", 3, 120, true, date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,"A103" );
		profesorPrueba.CalificacionMinima("A13", 0.5);
		assertEquals(0.5, profesorPrueba.mapaActividades.get("A13").getCalificacionMinima());
	}
	
	/// Test de Calificacion Minima Examen
	@Test
	void testCalificacionMinimaExamen() throws NombreRepetido, ParseException {
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
		actividadesPrevias.add("A101");
		actividadesPrevias.add("A103");

		ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
		actividadesSeguimiento.add("A102");

		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();

		HashMap<String, Object> atributosEspecificos = new HashMap<String, Object>();
		atributosEspecificos.put("calificacionMinima", 0.5);

		preguntas.add(new Pregunta("Para que es public, private, protected", null));
		atributosEspecificos.put("preguntas", preguntas);

		String fecha = "2021-12-01";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		Actividad actividadCreada = (Examen) profesorPrueba.crearActividad("A114", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Examen", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		profesorPrueba.CalificacionMinima("A114", 0.5);
		assertEquals(0.5, actividadCreada.getCalificacionMinima());
	}
	
	/// Test de Revisar estado de Quiz
	@Test
	void testRevisarEstadoQuiz() throws ParseException, NombreRepetido {
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
	actividadesPrevias.add("A101");
	actividadesPrevias.add("A103");
	
	ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
	actividadesSeguimiento.add("A102");
	
	ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();
	ArrayList<String> respuestas = new ArrayList<String>();
	respuestas.add("A");
	respuestas.add("B");
	respuestas.add("C");
	
	HashMap<String, Object> atributosEspecificos = new HashMap<String, Object >();
	atributosEspecificos.put("calificacionMinima", 0.5);
	atributosEspecificos.put("RespuestaCorrecta", respuestas.get(0));
	
	preguntas.add(new Pregunta("Para que es public, private, protected", respuestas));
	atributosEspecificos.put("preguntas", preguntas);
	
	
	String fecha = "2021-12-01";
	Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

	
	Actividad actividadCreada = (Quiz) profesorPrueba.crearActividad("A20", "Descripcion", "Objetivos", 3, 120, true, date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,"A103" );
	profesorPrueba.revisarEstadoActividad("A20", "LP106");
	assertEquals("Exitoso", actividadCreada.getEstado());
}
	
	///Test de Revisar estado de Examen
	@Test
	void testRevisarEstadoExamen() throws ParseException, NombreRepetido {
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
		actividadesPrevias.add("A101");
		actividadesPrevias.add("A103");

		ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
		actividadesSeguimiento.add("A102");

		ArrayList<Pregunta> preguntas = new ArrayList<Pregunta>();

		HashMap<String, Object> atributosEspecificos = new HashMap<String, Object>();
		atributosEspecificos.put("calificacionMinima", 0.5);

		preguntas.add(new Pregunta("Para que es public, private, protected", null));
		atributosEspecificos.put("preguntas", preguntas);

		String fecha = "2021-12-01";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		Actividad actividadCreada = (Examen) profesorPrueba.crearActividad("A15", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Examen", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		profesorPrueba.revisarEstadoActividad("A15", "LP106");
		assertEquals("Fallido", actividadCreada.getEstado());
	}
	
	///Test de Revisar estado de Tarea
	@Test
	void testRevisarEstadoTarea() throws ParseException, NombreRepetido {
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
		actividadesPrevias.add("A101");
		actividadesPrevias.add("A103");

		ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
		actividadesSeguimiento.add("A102");

		HashMap<String, Object> atributosEspecificos = new HashMap<String, Object>();
		atributosEspecificos.put("tipoEntrega", "Archivo");
		atributosEspecificos.put("tipoArchivo", "PDF");

		String fecha = "2021-12-01";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		Actividad actividadCreada = (Tarea) profesorPrueba.crearActividad("A16", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Tarea", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		profesorPrueba.revisarEstadoActividad("A16", "LP106");
		assertEquals("Fallido", actividadCreada.getEstado());
	}
	
	///Test de Revisar estado de Encuesta
	@Test
	void testRevisarEstadoEncuesta() throws ParseException, NombreRepetido {
		ArrayList<String> actividadesPrevias = new ArrayList<String>();
		actividadesPrevias.add("A101");
		actividadesPrevias.add("A103");

		ArrayList<String> actividadesSeguimiento = new ArrayList<String>();
		actividadesSeguimiento.add("A102");

		ArrayList<String> preguntas = new ArrayList<String>();
		preguntas.add("¿Te gusto la actividad?");

		HashMap<String, Object> atributosEspecificos = new HashMap<String, Object>();
		atributosEspecificos.put("preguntas", preguntas);

		String fecha = "2021-12-01";
		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

		Actividad actividadCreada = (Encuesta) profesorPrueba.crearActividad("A22", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Encuesta", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		profesorPrueba.revisarEstadoActividad("A22", "LP106");
		assertEquals("Exitoso", actividadCreada.getEstado());
	}
	
	///Test de Revisar estado de Recurso Educativo
	@Test
    void testRevisarEstadoRecursoEducativo() throws ParseException, NombreRepetido {
        ArrayList<String> actividadesPrevias = new ArrayList<>();
        actividadesPrevias.add("A101");
        actividadesPrevias.add("A103");

        ArrayList<String> actividadesSeguimiento = new ArrayList<>();
        actividadesSeguimiento.add("A102");

        HashMap<String, Object> atributosEspecificos = new HashMap<>();
        atributosEspecificos.put("tipoRecurso", "Video");
        atributosEspecificos.put("linkRecurso", "www.youtube.com");

        String fecha = "2021-12-01";
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(fecha);

        RecursoEducativo recurso = (RecursoEducativo) profesorPrueba.crearActividad("A17", "Descripcion", "Objetivos", 3, 120,
                true, date, "reseña", 0, 0, "Recurso Educativo", "LP106", actividadesPrevias, actividadesSeguimiento,
                atributosEspecificos, "A103");

        recurso.setEstado("Exitoso"); // Ensure estado is set before revising

        profesorPrueba.revisarEstadoActividad("A17", "LP106");
        assertEquals("Exitoso", recurso.getEstado());
    }
	

	
}
