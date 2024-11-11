package Pruebas;

import static org.junit.jupiter.api.Assertions.*;

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
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaLearningPaths;
import Perisistencia.PersistenciaUsuarios;
import Usuarios.Profesor;
import Usuarios.Usuario;

class PruebasProfesor {
	private Profesor profesorPrueba;

	@BeforeEach
    void setUp( ) throws Exception{
		profesorPrueba = new Profesor("P_1", "Ivan", "clave123", "ivan@uniandes.edu.co", "Profesor");
	}
	
	
	/// Test de crear LearningPath
	@Test
	void testCrearLearningPath() throws NombreRepetido {
		List<String> intereses = List.of("programación","dpoo");
		List<String> actividadesID = List.of("A_1","A_2","A_3");
		profesorPrueba.crearLearningPath("LP_1¨", "Como hacer una persistencia", "descripción", "objetivos",3
				, 120 , "P_1", actividadesID, intereses);
		assertEquals(1, profesorPrueba.learningPathsCreados.size());
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
	void testCrearQuiz() throws ParseException, NombreRepetido {
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

		
		Actividad actividadCreada = (Quiz) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120, true, date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,"A103" );
		assertEquals(1, profesorPrueba.mapaActividades.size());
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

		Actividad actividadCreada = (Tarea) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Tarea", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		assertEquals(1, profesorPrueba.mapaActividades.size());
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

		Actividad actividadCreada = (Examen) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Examen", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		assertEquals(1, profesorPrueba.mapaActividades.size());
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

		Actividad actividadCreada = (Encuesta) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Encuesta", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		assertEquals(1, profesorPrueba.mapaActividades.size());
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

		Actividad actividadCreada = (RecursoEducativo) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Recurso Educativo", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		assertEquals(1, profesorPrueba.mapaActividades.size());
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

		
		Actividad actividadCreada = (Quiz) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120, true, date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,"A103" );
		profesorPrueba.CalificacionMinima("A_110", 0.5);
		assertEquals(0.5, profesorPrueba.mapaActividades.get("A_110").getCalificacionMinima());
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

		Actividad actividadCreada = (Examen) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Examen", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		profesorPrueba.CalificacionMinima("A_110", 0.5);
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

	
	Actividad actividadCreada = (Quiz) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120, true, date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,"A103" );
	profesorPrueba.revisarEstadoActividad("A_110", "LP106");
	assertEquals("Fallido", actividadCreada.getEstado());
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

		Actividad actividadCreada = (Examen) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Examen", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		profesorPrueba.revisarEstadoActividad("A_110", "LP106");
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

		Actividad actividadCreada = (Tarea) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Tarea", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		profesorPrueba.revisarEstadoActividad("A_110", "LP106");
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

		Actividad actividadCreada = (Encuesta) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos", 3, 120,
				true, date, "reseña", 0, 0, "Encuesta", "LP106", actividadesPrevias, actividadesSeguimiento,
				atributosEspecificos, "A103");
		profesorPrueba.revisarEstadoActividad("A_110", "LP106");
		assertEquals("Exitoso", actividadCreada.getEstado());
	}
	
	///Test de Revisar estado de Recurso Educativo
	@Test
	void testRevisarEstadoRecursoEducativo() throws ParseException, NombreRepetido {
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

		Actividad actividadCreada = (RecursoEducativo) profesorPrueba.crearActividad("A110", "Descripcion", "Objetivos",
				3, 120, true, date, "reseña", 0, 0, "Recurso Educativo", "LP106", actividadesPrevias,
				actividadesSeguimiento, atributosEspecificos, "A103");
		profesorPrueba.revisarEstadoActividad("A_110", "LP106");
		assertEquals("Exitoso", actividadCreada.getEstado());
	}
	

	
}
