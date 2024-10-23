package main;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Actividades.Encuesta;
import Actividades.Actividad;
import Actividades.Examen;
import Actividades.Pregunta;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaLearningPaths;
import Perisistencia.PersistenciaUsuarios;
import Usuarios.Authenticator;
import Usuarios.Estudiante;
import Usuarios.Profesor;
import Usuarios.Usuario;

public class Central {

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
	private static final String usuarios ="src\\datos\\users.json";
	
	
	public static void main(String[] args) {	
		
		
		System.out.println("Iniciando sesión, por favor espere...");
		persistenciaActividades = new PersistenciaActividades();
		persistenciaUsuarios = new PersistenciaUsuarios();
		persistenciaLearningPaths = new PersistenciaLearningPaths();
		
		try {

			estudiantes=persistenciaUsuarios.cargarEstudiantes(usuarios);
			profesores=persistenciaUsuarios.cargarProfesores(usuarios);
			learningPaths=persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
			actividades = persistenciaActividades.cargarActividades(actividadesFile);
			
			System.out.println("Actividades: ");
			for (Actividad a: actividades) {
				System.out.println("ID: "+ a.getActividadID() + ". Tipo: "+ a.getTipoActividad());
			}
			
			System.out.println("Profesores: ");
			for (Profesor p : profesores) {
				System.out.println(p.getNombre());
			}
			
			System.out.println("Estudiantes: ");
			for (Estudiante e : estudiantes) {
				System.out.println(e.getNombre());
			}
			
			
			System.out.println("Learning Paths: ");
			for (LearningPath l : learningPaths) {
				System.out.println(l.getTitulo());
			}
			
			
			
			//Test quiz persistence
			ArrayList<String> actividadesID = new ArrayList<String>();
			actividadesID.add("A101");
			actividadesID.add("A103");
			actividadesID.add("A102");
			ArrayList<String> intereses = new ArrayList<String>();
			intereses.add("Java");
			intereses.add("Programacion");
			learningPaths.add(new LearningPath("LP105", "Aprendiendo a programar en Java", "Descripcion", "Objetivos", 3, 120, "P105", actividadesID, intereses));
			persistenciaActividades.salvarActividad("actividades.json", actividades.get(1));
			System.out.println("\n");
			System.out.println("Se han leido los archivos y cargado los datos correctamente.");
			System.out.println("\n");
			System.out.println("Bienvenido al sistema de aprendizaje de la Universidad de los Andes. Por favor inicie sesión: ");
			System.out.println("\n");
			System.out.println("1. Iniciar sesión como Usuario");
			System.out.println("\n");
			
			Estudiante estudiante = new Estudiante( "U105", "Juan Perez", "1234", "J.perez@uniandes.edu.co", "Estudiante");
			Estudiante estudiante1 = new Estudiante( "U106", "Maria Perez", "1234", "m.perez@uniandes.edu.co", "Estudiante");
			Profesor profesor = new Profesor("P105", "Carlos Perez", "1234", "C.perez@uniandes.edu.co", "Profesor");
			estudiantes.add(estudiante);
			estudiantes.add(estudiante1);
			profesores.add(profesor);
			estudiante.profesores.put("P105", profesor);
			estudiante1.profesores.put("P105", profesor);
			
			System.out.println("2. Inicio sesion como estudiante");
			if(estudiante.iniciarSesion("U105", "1234") ) {
                System.out.println("Inicio de sesion exitoso: Estudiante "+ estudiante.getUsuarioID());
            }
            else {
                System.out.println("Inicio de sesion fallido: Estudiante");
            }
			System.out.println("\n");
			System.out.println("3. Inicio sesion como profesor");
			if(profesor.iniciarSesion("P105", "1234")) {
                System.out.println("Inicio de sesion exitoso: Profesor "+ profesor.getUsuarioID());
            }
            else {
                System.out.println("Inicio de sesion fallido: Profesor");
            }
			
			System.out.println("\n");
			System.out.println("4.Profesor crea un learning Path");
			learningPaths.add(profesor.crearLearningPath("LP106", "Aprendiendo a programar en Java", "Descripcion", "Objetivos", 3, 120, "P105", actividadesID, intereses));
			System.out.println("Learning Paths creado por el profesor: "+ profesor.crearLearningPath("LP106", "Aprendiendo a programar en Java", "Descripcion", "Objetivos", 
					3, 120, "P105", actividadesID, intereses).getLearningPathID()  );
			
			
			
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
			
			System.out.println("\n");
			System.out.println("4.Profesor crea una actividad y la añade al learning Path");
			Actividad actividadCreada = (Quiz) profesor.crearActividad("A110", "Descripcion", "Objetivos", 3, 120, true, date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos,"A103" );
			System.out.println("Actividad creada por el profesor: "+ actividadCreada.getActividadID());
			System.out.println("Numero de actividades en el learning Path: "+ profesor.getLearningPathsCreados().get("LP106").getActividades().size());
			
			Actividad actividadCreada1= profesor.crearActividad("A101", "Descripcion", "Objetivos", 3, 120, true, date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos, "A103" );
			Actividad actividadCreada2= profesor.crearActividad("A103", "Descripcion", "Objetivos", 3, 120, true, date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos, "A103" );
			Actividad actividadCreada3= profesor.crearActividad("A102", "Descripcion", "Objetivos", 3, 120, true, date, "reseña", 0, 0, "Quiz", "LP106", actividadesPrevias, actividadesSeguimiento, atributosEspecificos, "A103" );
			
			profesor.CalificacionMinima("A110", 60.1 );
			System.out.println("Calificacion minima de la actividad: "+ ((Quiz) actividadCreada).getCalificacionMinima());
			
			System.out.println("\n");
			System.out.println("5.El estudiante busca recomendaciones para ver a que learning path inscribirse:");
			System.out.println("Se le recomienda inscribirse a los learning paths:");
			System.out.println(estudiante.obtenerRecomendacion("Java", "P105"));
			
			System.out.println("\n");
			System.out.println("6. El estudiante se inscribe al learning Path deseado");
			System.out.println(estudiante.inscribirLearningPath("LP106", "P105")); 
			estudiante1.inscribirLearningPath("LP106", "P105");
			
			System.out.println("\n");
			System.out.println("7.El profesor revisa el estado de una actividad");
			System.out.println("Estado de la actividad: "+ actividadCreada.getEstado());
			profesor.revisarEstadoActividad("A110", "LP106");
			System.out.println("Estado de la actividad: "+ actividadCreada.getEstado());
		
			System.out.println("\n");
			System.out.println("8. El estudiante completa la actividad exitosamente, entonces cambia su estado");
			estudiante.completarActividad("A103", "LP106");
			estudiante.completarActividad("A110", "LP106");
			
			System.out.println("\n");
			System.out.println("14.El estudiante mira la fecha limite de una actividad");
			System.out.println("La fecha limite de la actividad es: "+ actividadCreada.getFechaLimite());
			estudiante1.completarActividad("A103", "LP106");
			System.out.println("Estado de la actividad: "+ actividadCreada.getEstado());
			
			ArrayList<String> actividadesID1 = new ArrayList<String>();
			System.out.println("\n");
			System.out.println("9. El estudiante revisa que actividades todavia debe completar");
			for(Actividad actividad : estudiante.actividadesDisponibles("LP106")) {
				actividadesID1.add(actividad.getActividadID());
			}
			System.out.println("Todavia debe realizar la actividad: "+ actividadesID1);
			
			ArrayList<String> actividadesID2 = new ArrayList<String>();
			estudiante.completarActividad("A102", "LP106");
			estudiante.completarActividad("A101", "LP106");
			for(Actividad actividad : estudiante.actividadesDisponibles("LP106")) {
				actividadesID2.add(actividad.getActividadID());
			}
			
			System.out.println("Todavia debe realizar la actividad: "+ actividadesID2);
			
			System.out.println("\n");
			System.out.println("10. Se revisa el porcentaje de exito del estudiante en el learningPath");
			System.out.println("El porcentaje de exito es "+ estudiante.getProgresoLearningPath("LP106")*100+"%");
			
			System.out.println("\n");
			System.out.println("11.El estudiante agrega feedback y el profesor lo revisa");
			estudiante.enviarFeedback("LP106", "Excelente curso", 5, "U105");
			estudiante1.enviarFeedback("LP106", "Buen curso", 4, "U106");
			System.out.println("Feedback del learningPath seleccionado: "+ 
			                     profesor.revisarFeedback("LP106"));
			
			System.out.println("\n");
			System.out.println("12.El profesor revisa el rating del learning path");
			System.out.println("El rating del learning path es: "+ profesor.calcularRating("LP106"));
			
			System.out.println("\n");
			System.out.println("13.El profesor revisa el progreso del learning path del estudiante");
			System.out.println("Los detalles del progreso del learning path estudiante son: "+ profesor.verProgresoEstudiante("U106", "LP106"));
			

			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	



}
