package Consola;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;

import Usuarios.Estudiante;
import Usuarios.Profesor;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;
import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Perisistencia.PersistenciaUsuarios;
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaLearningPaths;
import Perisistencia.PersistenciaProgreso;
import Actividades.Pregunta;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import Exceptions.NombreRepetido;

public class ConsolaProfesor {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Profesor profesorActual;
    private static PersistenciaUsuarios persistenciaUsuarios = new PersistenciaUsuarios();
    private static PersistenciaActividades persistenciaActividades = new PersistenciaActividades();
    private static PersistenciaLearningPaths persistenciaLearningPaths = new PersistenciaLearningPaths();
    private static PersistenciaProgreso persistenciaProgreso = new PersistenciaProgreso();
    private static final String usuariosFile = "src/datos/users.json";
    private static final String actividadesFile = "src/datos/activities.json";
    private static final String learningPathsFile = "src/datos/learning_paths.json";
    private static final String progresoFile = "src/datos/progreso.json";
    private static List<LearningPath> learningPaths;
    private static Map<String, Profesor> profesores = new HashMap<>();
    private static List<Actividad> actividades;
    private static List<Estudiante> estudiantes;
    private static List<Profesor> profesoresLista;
    private static List<Progreso> progresos;
    
    
    public static void main(String[] args) throws Exception { 
    	cargarActividades();
        cargarLearningPaths();
        cargarProgresos();
        cargarProgresoLP();
        cargarProfesores();
        cargarLpProfesoress();
        cargarActividadesLP();
        cargarEstudiantes();
        
       
        boolean authenticated = false;
        
        
        if (iniciarSesion() == 1) {
            while (!authenticated) {
                if (authenticar(null, null)) {
                    authenticated = true;
                    
                    menu();
                } else {
                    System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
                }
            }
        } else {
            boolean registered = false;
            while (!registered) {
                registrarse(null, null, null, null);
                System.out.println("Usuario registrado");
                System.out.println("Iniciar sesión");
                if (authenticar(null, null)) {
                    registered = true;
                    menu();
                    
                } else {
                    System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
                }
            }
        }
    }

    public static void cargarProfesores() {
		try {
			profesoresLista = persistenciaUsuarios.cargarProfesores(usuariosFile);
			for (Profesor profe : profesoresLista) {
                profesores.put(profe.getUsuarioID(), profe);
			}
            System.out.println("Informacion cargada" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    public static void cargarLearningPaths() {
        try {
            learningPaths = persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void cargarActividadesLP() {
        try {
            persistenciaLearningPaths.cargarActividadesDelLearningPath(actividades, learningPaths);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void cargarEstudiantes() {
        try {
            estudiantes = persistenciaUsuarios.cargarEstudiantes(usuariosFile);
            System.out.println("Datos de estudiantes cargados correctamente\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static void cargarActividades() {
        try {
            actividades = persistenciaActividades.cargarActividades(actividadesFile);
            System.out.println("Actividades cargadas correctamente: " + actividades.size() + "\n");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void cargarLpProfesoress() {
        try {
        	System.out.println("Cargando Learning Paths de profesores...");
        	for (Profesor profesor : profesores.values()) {
        	}
        	for (LearningPath learningPath : learningPaths) {
        	}
            persistenciaUsuarios.cargarLearningPathsProfesor(learningPaths, profesores);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public static void cargarProgresoLP() {
		try {
			persistenciaLearningPaths.cargarProgresoDelLearningPath(progresos, learningPaths);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cargarProgresos() {
        try {
            progresos = persistenciaProgreso.cargarProgreso(progresoFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
    private static int iniciarSesion() {
        int opcion = 0;
        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.println("1. Iniciar Sesion");
                System.out.println("2. Registrarse");
                System.out.print("Seleccione una opción: ");
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                validInput = true;
            } catch (InputMismatchException e) {
                System.out.println("Por favor, ingrese un número válido.");
                scanner.nextLine(); // Clear the invalid input
            }
        }
        return opcion;
    }

    public static boolean authenticar(String usuarioID, String contrasena) {
        
		try {			
			for (Profesor profesor : profesoresLista) {
	            if (profesor.getUsuarioID().equals(usuarioID) && profesor.getContraseña().equals(contrasena)) {
	                profesorActual = profesor;
	                return true;
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
     
        return false;
    }
    
    public static void registrarse(String usuarioID, String nombre, String contrasena, String email) {
    	
    	
    	Profesor profesor = new Profesor(usuarioID, nombre, contrasena, email, "Profesor");
    	
    	persistenciaUsuarios.salvarProfesor(usuariosFile, profesor.getUsuarioID(), profesor.getNombre(),
				profesor.getContraseña(), profesor.getEmail(), profesor.getTipoUsuario());	
    }

    private static void menu() throws Exception {
        int option;
        do {
        	System.out.println("\n");
            System.out.println("---- Profesor Interface ----");
            System.out.println("1. Crear Actividad");
            System.out.println("2. Crear Learning Path");
            System.out.println("3. Revisar Estado de Actividad");
            System.out.println("4. Ver Progreso de Estudiante");
            System.out.println("5. Revisar Feedback");
            System.out.println("6. Calcular Rating");
            System.out.println("7. Salir");
            System.out.println("---------------------------");
            System.out.println("\n");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine(); 
            handleOption(option);
        } while (option != 7);
    }

    private static void handleOption(int option) throws Exception {
        switch (option) {
            case 1 -> crearActividad();
            case 2 -> crearLearningPath(null, null, null, null, option, option, null, null, null);
            case 3 -> revisarEstadoActividad();
            case 4 -> verProgresoEstudiante();
            case 5 -> revisarFeedback();
            case 6 -> calcularRating();
            case 7 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción inválida.");
        }
    }


    private static void crearActividad() throws Exception {
        System.out.println("Ingrese el tipo de actividad");
        System.out.println("1. Encuesta");
        System.out.println("2. Examen");
        System.out.println("3. Quiz");
        System.out.println("4. Recurso educativo");
        System.out.println("5. Tarea");
        System.out.println("\n");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        if (opcion == 1) {

		System.out.print("ID de la Actividad: ");
		String actividadID = "";
		while (actividadID.isEmpty()) {
		    actividadID = scanner.nextLine();
		    if (actividadID.isEmpty()) {
		        System.out.println("Por favor, ingrese un ID de actividad válido.");
		    }
		}
		
		System.out.print("Descripción: ");
		String descripcion = "";
		while (descripcion.isEmpty()) {
		    descripcion = scanner.nextLine();
		    if (descripcion.isEmpty()) {
		        System.out.println("Por favor, ingrese una descripción válida.");
		    }
		}
		
		System.out.print("Objetivo: ");
		String objetivo = "";
		while (objetivo.isEmpty()) {
		    objetivo = scanner.nextLine();
		    if (objetivo.isEmpty()) {
		        System.out.println("Por favor, ingrese un objetivo válido.");
		    }
		}
		
		int nivelDificultad = -1;
		while (nivelDificultad < 0 || nivelDificultad > 10) {
		    System.out.print("Nivel de dificultad, indique un numero entre 0 y 10: ");
		    try {
		        nivelDificultad = Integer.parseInt(scanner.nextLine());
		    } catch (NumberFormatException e) {
		        System.out.println("Por favor, ingrese un número válido entre 0 y 10.");
		    }
		} 
		
		int duracionEsperada = -1;
		while (duracionEsperada < 0) {
		    System.out.print("Duración esperada, indique los minutos esperados: ");
		    try {
		        duracionEsperada = Integer.parseInt(scanner.nextLine());
		    } catch (NumberFormatException e) {
		        System.out.println("Por favor, ingrese un número válido para la duración esperada.");
		    }
		}
		
		boolean esObligatoria = false;
		boolean validInput = false;
		while (!validInput) {
		    System.out.print("Es obligatoria, Ingrese True or False: ");
		    String input = scanner.nextLine();
		    if (input.equalsIgnoreCase("true") || input.equalsIgnoreCase("false")) {
		        esObligatoria = Boolean.parseBoolean(input);
		        validInput = true;
		    } else {
		        System.out.println("Por favor, ingrese 'True' o 'False'.");
		    }
		}
		
		String fechaLimite = "";
		Date fecha = new Date();
		long fechaLimiteMillis = 0;
		while (fechaLimiteMillis == 0) {
		    System.out.print("Fecha límite - dd/MM/yyyy: ");
		    fechaLimite = scanner.nextLine();
		    try {
		        fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimite);
		        fechaLimiteMillis = fecha.getTime();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese una fecha válida en el formato dd/MM/yyyy.");
		    }
		}
		
		System.out.print("Reseñas: ");
		String resenas = scanner.nextLine();
		
		int calificacion = -1;
		while (calificacion < 0) {
		    System.out.print("Calificación, ingrese la calificacion esperada: ");
		    try {
		        calificacion = Integer.parseInt(scanner.nextLine());
		    } catch (NumberFormatException e) {
		        System.out.println("Por favor, ingrese un número válido para la calificación.");
		    }
		}
		
		Double resultado = null;
		while (resultado == null) {
		    System.out.print("Resultado, ingrese el resultado: ");
		    try {
		        resultado = Double.parseDouble(scanner.nextLine());
		    } catch (NumberFormatException e) {
		        System.out.println("Por favor, ingrese un número válido para el resultado.");
		    }
		}
		
		System.out.print("Actividades previas, ingrese id separado por coma: ");
		List<String> actividadesPrevias = new ArrayList<>();
		String actividadPrevia = scanner.nextLine();
		actividadesPrevias.add(actividadPrevia);
		
		System.out.print("Actividades de seguimiento, ingrese id separado por coma: ");
		List<String> actividadesSeguimiento = new ArrayList<>();
		String actividadSeguimiento = scanner.nextLine();
		actividadesSeguimiento.add(actividadSeguimiento);
		
		System.out.print("Preguntas: \n");
		
		List<Pregunta> preguntas = new ArrayList<>();
		boolean moreQuestions = true;
		while (moreQuestions) {
		    System.out.print("Ingrese la pregunta: ");
		    String preguntaID = scanner.nextLine();
		    List<String> opciones = new ArrayList<>();
		    boolean moreOptions = true;
		    while (moreOptions) {
		        System.out.print("Ingrese una opción (o 'fin' para terminar): ");
		        String opcion1 = scanner.nextLine();
		        if (opcion1.equalsIgnoreCase("fin")) {
		            moreOptions = false;
		        } else {
		            opciones.add(opcion1);
		        }
		    }
		    preguntas.add(new Pregunta(preguntaID, opciones));
		    System.out.print("¿Desea agregar otra pregunta? (si/no): ");
		    String respuesta = scanner.nextLine();
		    if (respuesta.equalsIgnoreCase("no")) {
		        moreQuestions = false;
		    }
		}
		
		System.out.print("Estos son los learningPaths disponibles: ");
		System.out.println("\n");
		List<LearningPath> learningPaths = persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
		for (LearningPath learningPath : learningPaths) {
		    System.out.println(learningPath.getLearningPathID());
		}
		System.out.println("\nIngrese el ID del learning Path al que quiere que pertenezca la actividad: ");
		String learningPathID = scanner.nextLine();
		String tipo = "Encuesta";
		
		HashMap<String, Object> parametrosEspecificos = new HashMap<>();
		parametrosEspecificos.put("Preguntas", preguntas);
		
		Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
		        duracionEsperada, esObligatoria, fecha, resenas,
		        resultado, calificacion, tipo, learningPathID, actividadesPrevias, actividadesSeguimiento,
		        parametrosEspecificos, actividadPrevia);
		
		persistenciaActividades.salvarActividad(actividadesFile, actividad);



		} else if (opcion == 2) {
		    String actividadID = "";
		    while (actividadID.isEmpty()) {
		        System.out.print("ID de la Actividad: ");
		        actividadID = scanner.nextLine();
		    }
		String descripcion = "";
		while (descripcion.isEmpty()) {
		    System.out.print("Descripción: ");
		    descripcion = scanner.nextLine();
		}
		
		String objetivo = "";
		while (objetivo.isEmpty()) {
		    System.out.print("Objetivo: ");
		    objetivo = scanner.nextLine();
		}
		
		int nivelDificultad = -1;
		while (nivelDificultad < 0 || nivelDificultad > 10) {
		    System.out.print("Nivel de dificultad, indique un numero entre 0 y 10: ");
		    try {
		        nivelDificultad = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		int duracionEsperada = -1;
		while (duracionEsperada < 0) {
		    System.out.print("Duración esperada, indique los minutos esperados: ");
		    try {
		        duracionEsperada = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		boolean esObligatoria = false;
		boolean validInput = false;
		while (!validInput) {
		    System.out.print("Es obligatoria, Ingrese True or False: ");
		    try {
		        esObligatoria = scanner.nextBoolean();
		        validInput = true;
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese True o False.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		String fechaLimite = "";
		Date fecha = new Date();
		long fechaLimiteMillis = 0;
		while (fechaLimiteMillis == 0) {
		    System.out.print("Fecha límite: ");
		    fechaLimite = scanner.nextLine();
		    try {
		        fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimite);
		        fechaLimiteMillis = fecha.getTime();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese una fecha válida en el formato dd/MM/yyyy.");
		    }
		}
		
		System.out.print("Reseñas: ");
		String resenas = scanner.nextLine();
		
		int calificacion = -1;
		while (calificacion < 0) {
		    System.out.print("Calificación: ");
		    try {
		        calificacion = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		Double resultado = null;
		while (resultado == null) {
		    System.out.print("Resultado: ");
		    try {
		        resultado = Double.parseDouble(scanner.nextLine());
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		    }
		}
		
		
		System.out.print("Actividades previas: ");
		List<String> actividadesPrevias = new ArrayList<>();
		String actividadPrevia = scanner.nextLine();
		actividadesPrevias.add(actividadPrevia);
		
		System.out.print("Actividades de seguimiento: ");
		List<String> actividadesSeguimiento = new ArrayList<>();
		String actividadSeguimiento = scanner.nextLine();
		actividadesSeguimiento.add(actividadSeguimiento);
		
		System.out.print("Preguntas: \n");
		
		List<Pregunta> preguntas = new ArrayList<>();
		boolean moreQuestions = true;
		while (moreQuestions) {
		    System.out.print("Ingrese la pregunta: ");
		    String preguntaID = scanner.nextLine();
		    List<String> opciones = new ArrayList<>();
		    boolean moreOptions = true;
		    while (moreOptions) {
		        System.out.print("Ingrese una opción (o 'fin' para terminar): ");
		        String opcion1 = scanner.nextLine();
		        if (opcion1.equalsIgnoreCase("fin")) {
		            moreOptions = false;
		        } else {
		            opciones.add(opcion1);
		        }
		    }
		    preguntas.add(new Pregunta(preguntaID, opciones));
		    System.out.print("¿Desea agregar otra pregunta? (si/no): ");
		    String respuesta = scanner.nextLine();
		    if (respuesta.equalsIgnoreCase("no")) {
		        moreQuestions = false;
		    }
		}
		
		System.out.print("Estos son los learningPaths disponibles: ");
		System.out.println("\n");
		List<LearningPath> learningPaths = persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
		for (LearningPath learningPath : learningPaths) {
		    System.out.println(learningPath.getLearningPathID());
		}
		System.out.println("\nIngrese el ID del learning Path al que quiere que pertenezca la actividad: ");
		String learningPathID = scanner.nextLine();
		String tipo = "Examen";
		
		System.out.print("Calificación mínima: ");
		Double calificacionMinima = scanner.nextDouble();
		HashMap<String, Object> parametrosEspecificos = new HashMap<>();
		parametrosEspecificos.put("Preguntas", preguntas);
		parametrosEspecificos.put("calificacionMinima", calificacionMinima);
		
		System.out.println(parametrosEspecificos.keySet());
		System.out.println(parametrosEspecificos.values());
		
		Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
		        duracionEsperada, esObligatoria, fecha, resenas,
		        resultado, calificacion, tipo, learningPathID, actividadesPrevias, actividadesSeguimiento,
		        parametrosEspecificos, actividadPrevia);
		
		
		
		persistenciaActividades.salvarActividad(actividadesFile, actividad);
		
		} else if (opcion == 3) {
		    String actividadID = "";
		    while (actividadID.isEmpty()) {
		        System.out.print("ID de la Actividad: ");
		        actividadID = scanner.nextLine();
		    }
		String descripcion = "";
		while (descripcion.isEmpty()) {
		    System.out.print("Descripción: ");
		    descripcion = scanner.nextLine();
		}
		
		String objetivo = "";
		while (objetivo.isEmpty()) {
		    System.out.print("Objetivo: ");
		    objetivo = scanner.nextLine();
		}
		
		int nivelDificultad = -1;
		while (nivelDificultad < 0 || nivelDificultad > 10) {
		    System.out.print("Nivel de dificultad, indique un numero entre 0 y 10: ");
		    try {
		        nivelDificultad = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		int duracionEsperada = -1;
		while (duracionEsperada < 0) {
		    System.out.print("Duración esperada, indique los minutos esperados: ");
		    try {
		        duracionEsperada = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		boolean esObligatoria = false;
		boolean validInput = false;
		while (!validInput) {
		    System.out.print("Es obligatoria, Ingrese True or False: ");
		    try {
		        esObligatoria = scanner.nextBoolean();
		        validInput = true;
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese True o False.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		String fechaLimite = "";
		Date fecha = new Date();
		long fechaLimiteMillis = 0;
		while (fechaLimiteMillis == 0) {
		    System.out.print("Fecha límite: ");
		    fechaLimite = scanner.nextLine();
		    try {
		        fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimite);
		        fechaLimiteMillis = fecha.getTime();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese una fecha válida en el formato dd/MM/yyyy.");
		    }
		}
		
		System.out.print("Reseñas: ");
		String resenas = scanner.nextLine();
		
		int calificacion = -1;
		while (calificacion < 0) {
		    System.out.print("Calificación: ");
		    try {
		        calificacion = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		Double resultado = null;
		while (resultado == null) {
		    System.out.print("Resultado: ");
		    try {
		        resultado = Double.parseDouble(scanner.nextLine());
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		    }
		}
		
		System.out.print("Actividades previas: ");
		List<String> actividadesPrevias = new ArrayList<>();
		String actividadPrevia = scanner.nextLine();
		actividadesPrevias.add(actividadPrevia);
		
		System.out.print("Actividades de seguimiento: ");
		List<String> actividadesSeguimiento = new ArrayList<>();
		String actividadSeguimiento = scanner.nextLine();
		actividadesSeguimiento.add(actividadSeguimiento);
		
		System.out.print("Preguntas: \n");
		
		List<Pregunta> preguntas = new ArrayList<>();
		boolean moreQuestions = true;
		while (moreQuestions) {
		    System.out.print("Ingrese la pregunta: ");
		    String preguntaID = scanner.nextLine();
		    List<String> opciones = new ArrayList<>();
		    boolean moreOptions = true;
		    while (moreOptions) {
		        System.out.print("Ingrese una opción (o 'fin' para terminar): ");
		        String opcion1 = scanner.nextLine();
		        if (opcion1.equalsIgnoreCase("fin")) {
		            moreOptions = false;
		        } else {
		            opciones.add(opcion1);
		        }
		    }
		    preguntas.add(new Pregunta(preguntaID, opciones));
		    System.out.print("¿Desea agregar otra pregunta? (si/no): ");
		    String respuesta = scanner.nextLine();
		    if (respuesta.equalsIgnoreCase("no")) {
		        moreQuestions = false;
		    }
		}
		
		System.out.print("Estos son los learningPaths disponibles: ");
		System.out.println("\n");
		List<LearningPath> learningPaths = persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
		for (LearningPath learningPath : learningPaths) {
		    System.out.println(learningPath.getLearningPathID());
		}
		System.out.println("\nIngrese el ID del learning Path al que quiere que pertenezca la actividad: ");
		String learningPathID = scanner.nextLine();
		String tipo = "Quiz";
		
		
		System.out.print("Calificación mínima: ");
		Double calificacionMinima = scanner.nextDouble();
		HashMap<String, Object> parametrosEspecificos = new HashMap<>();
		parametrosEspecificos.put("Preguntas", preguntas);
		parametrosEspecificos.put("calificacionMinima", calificacionMinima);
		
		
		Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
		        duracionEsperada, esObligatoria, fecha, resenas,
		        resultado, calificacion, tipo, learningPathID, actividadesPrevias, actividadesSeguimiento,
		        parametrosEspecificos, actividadPrevia);
		
		persistenciaActividades.salvarActividad(actividadesFile, actividad);
		
		} else if (opcion == 4) {
		    String actividadID = "";
		    while (actividadID.isEmpty()) {
		        System.out.print("ID de la Actividad: ");
		        actividadID = scanner.nextLine();
		    }
		String descripcion = "";
		while (descripcion.isEmpty()) {
		    System.out.print("Descripción: ");
		    descripcion = scanner.nextLine();
		}
		
		String objetivo = "";
		while (objetivo.isEmpty()) {
		    System.out.print("Objetivo: ");
		    objetivo = scanner.nextLine();
		}
		
		int nivelDificultad = -1;
		while (nivelDificultad < 0 || nivelDificultad > 10) {
		    System.out.print("Nivel de dificultad, indique un numero entre 0 y 10: ");
		    try {
		        nivelDificultad = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		int duracionEsperada = -1;
		while (duracionEsperada < 0) {
		    System.out.print("Duración esperada, indique los minutos esperados: ");
		    try {
		        duracionEsperada = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		boolean esObligatoria = false;
		boolean validInput = false;
		while (!validInput) {
		    System.out.print("Es obligatoria, Ingrese True or False: ");
		    try {
		        esObligatoria = scanner.nextBoolean();
		        validInput = true;
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese True o False.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		String fechaLimite = "";
		Date fecha = new Date();
		long fechaLimiteMillis = 0;
		while (fechaLimiteMillis == 0) {
		    System.out.print("Fecha límite: ");
		    fechaLimite = scanner.nextLine();
		    try {
		        fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimite);
		        fechaLimiteMillis = fecha.getTime();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese una fecha válida en el formato dd/MM/yyyy.");
		    }
		}
		
		System.out.print("Reseñas: ");
		String resenas = scanner.nextLine();
		
		int calificacion = -1;
		while (calificacion < 0) {
		    System.out.print("Calificación: ");
		    try {
		        calificacion = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		Double resultado = null;
		while (resultado == null) {
		    System.out.print("Resultado: ");
		    try {
		        resultado = Double.parseDouble(scanner.nextLine());
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		    }
		}
		
		System.out.print("Actividades previas: ");
		List<String> actividadesPrevias = new ArrayList<>();
		String actividadPrevia = scanner.nextLine();
		actividadesPrevias.add(actividadPrevia);
		
		System.out.print("Actividades de seguimiento: ");
		List<String> actividadesSeguimiento = new ArrayList<>();
		String actividadSeguimiento = scanner.nextLine();
		actividadesSeguimiento.add(actividadSeguimiento);
		
		System.out.print("Ingrese el link: ");
		String link = scanner.nextLine();
		
		System.out.print("Ingrese el tipo de recurso: ");
		String tipoRec = scanner.nextLine();
		
		System.out.print("Estos son los learningPaths disponibles: ");
		System.out.println("\n");
		List<LearningPath> learningPaths = persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
		for (LearningPath learningPath : learningPaths) {
		    System.out.println(learningPath.getLearningPathID());
		}
		System.out.print("\nLearning Path ID: ");
		String learningPathID = scanner.nextLine();
		String tipo = "Recurso Educativo";
		
		HashMap<String, Object> parametrosEspecificos = new HashMap<>();
		parametrosEspecificos.put("Link", link);
		parametrosEspecificos.put("Tipo de recurso", tipoRec);
		
		Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
		        duracionEsperada, esObligatoria, fecha, resenas,
		        resultado, calificacion, tipo, learningPathID, actividadesPrevias, actividadesSeguimiento,
		        parametrosEspecificos, actividadPrevia);
		

		
		persistenciaActividades.salvarActividad(actividadesFile, actividad);
		
		} else if (opcion == 5) {
		    String actividadID = "";
		    while (actividadID.isEmpty()) {
		        System.out.print("ID de la Actividad: ");
		        actividadID = scanner.nextLine();
		    }
		String descripcion = "";
		while (descripcion.isEmpty()) {
		    System.out.print("Descripción: ");
		    descripcion = scanner.nextLine();
		}
		
		String objetivo = "";
		while (objetivo.isEmpty()) {
		    System.out.print("Objetivo: ");
		    objetivo = scanner.nextLine();
		}
		
		int nivelDificultad = -1;
		while (nivelDificultad < 0 || nivelDificultad > 10) {
		    System.out.print("Nivel de dificultad, indique un numero entre 0 y 10: ");
		    try {
		        nivelDificultad = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		int duracionEsperada = -1;
		while (duracionEsperada < 0) {
		    System.out.print("Duración esperada, indique los minutos esperados: ");
		    try {
		        duracionEsperada = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		boolean esObligatoria = false;
		boolean validInput = false;
		while (!validInput) {
		    System.out.print("Es obligatoria, Ingrese True or False: ");
		    try {
		        esObligatoria = scanner.nextBoolean();
		        validInput = true;
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese True o False.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		String fechaLimite = "";
		Date fecha = new Date();
		long fechaLimiteMillis = 0;
		while (fechaLimiteMillis == 0) {
		    System.out.print("Fecha límite: ");
		    fechaLimite = scanner.nextLine();
		    try {
		        fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimite);
		        fechaLimiteMillis = fecha.getTime();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese una fecha válida en el formato dd/MM/yyyy.");
		    }
		}
		
		System.out.print("Reseñas: ");
		String resenas = scanner.nextLine();
		
		int calificacion = -1;
		while (calificacion < 0) {
		    System.out.print("Calificación: ");
		    try {
		        calificacion = scanner.nextInt();
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		        scanner.nextLine(); // Consume newline
		    }
		}
		scanner.nextLine(); // Consume newline
		
		Double resultado = null;
		while (resultado == null) {
		    System.out.print("Resultado: ");
		    try {
		        resultado = Double.parseDouble(scanner.nextLine());
		    } catch (Exception e) {
		        System.out.println("Por favor, ingrese un número válido.");
		    }
		}
		
		System.out.print("Actividades previas: ");
		List<String> actividadesPrevias = new ArrayList<>();
		String actividadPrevia = scanner.nextLine();
		actividadesPrevias.add(actividadPrevia);
		
		System.out.print("Actividades de seguimiento: ");
		List<String> actividadesSeguimiento = new ArrayList<>();
		String actividadSeguimiento = scanner.nextLine();
		actividadesSeguimiento.add(actividadSeguimiento);
		
		System.out.print("Preguntas: \n");
		
		List<Pregunta> preguntas = new ArrayList<>();
		boolean moreQuestions = true;
		while (moreQuestions) {
		    System.out.print("Ingrese la pregunta: ");
		    String preguntaID = scanner.nextLine();
		    List<String> opciones = new ArrayList<>();
		    boolean moreOptions = true;
		    while (moreOptions) {
		        System.out.print("Ingrese una opción (o 'fin' para terminar): ");
		        String opcion1 = scanner.nextLine();
		        if (opcion1.equalsIgnoreCase("fin")) {
		            moreOptions = false;
		        } else {
		            opciones.add(opcion1);
		        }
		    }
		    preguntas.add(new Pregunta(preguntaID, opciones));
		    System.out.print("¿Desea agregar otra pregunta? (si/no): ");
		    String respuesta = scanner.nextLine();
		    if (respuesta.equalsIgnoreCase("no")) {
		        moreQuestions = false;
		    }
		}
		
		System.out.print("Estos son los learningPaths disponibles: ");
		System.out.println("\n");
		List<LearningPath> learningPaths = persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
		for (LearningPath learningPath : learningPaths) {
		    System.out.println(learningPath.getLearningPathID());
		}
		System.out.println("\nIngrese el ID del learning Path al que quiere que pertenezca la actividad: ");
		String learningPathID = scanner.nextLine();
		String tipo = "Tarea";

        HashMap<String, Object> parametrosEspecificos = new HashMap<>();
        System.out.print("Ingrese las instrucciones de la tarea: ");
        String instrucciones = scanner.nextLine();
        System.out.print("Ingrese el estado de la tarea: ");
        String estado = scanner.nextLine();
        parametrosEspecificos.put("Preguntas", preguntas);
        parametrosEspecificos.put("Instrucciones", instrucciones);
        parametrosEspecificos.put("Estado", estado);

            Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
                     duracionEsperada, esObligatoria, fecha, resenas,
                     resultado, calificacion, tipo, learningPathID, actividadesPrevias, actividadesSeguimiento,
                    parametrosEspecificos, actividadPrevia);
            
            persistenciaActividades.salvarActividad(actividadesFile, actividad);

        } else {
            System.out.println("Opción inválida.");
        }
    }
    


public static void crearLearningPath(String LearningPathID, String titulo, String descripcion, String objetivos, 
		int nivelDificultad, int duracion, String profesorID, List<String> actividadesID, List<String> intereses
		) throws NombreRepetido {
    
    
    profesorActual.crearLearningPath(LearningPathID, titulo, descripcion, objetivos, 
    		nivelDificultad, duracion, profesorID, actividadesID, intereses);

    persistenciaLearningPaths.salvarLearningPath(learningPathsFile, LearningPathID,
            titulo, descripcion, objetivos, nivelDificultad, duracion, profesorID,
            actividadesID, intereses);
}
 




	private static void revisarEstadoActividad() {
		boolean validInput = false;
		String actividadID = "";
		String learningPathID = "";
	
		while (!validInput) {
			System.out.println("\n");
			System.out.print("ID de la Actividad: ");
			actividadID = scanner.nextLine();
	
			System.out.print("ID del Learning Path: ");
			learningPathID = scanner.nextLine();
	
			LearningPath lp = null;
			for (LearningPath learningPath : learningPaths) {
				if (learningPath.getLearningPathID().equals(learningPathID)) {
					lp = learningPath;
					break;
				}
			}
	
			if (lp != null && lp.getActividades().containsKey(actividadID)) {
				validInput = true;
				profesorActual.revisarEstadoActividad(actividadID, learningPathID);
			} else {
				System.out.println("Learning Path o Actividad no encontrados. Por favor, intente de nuevo.");
			}
		}
	}

	private static void verProgresoEstudiante() {
		boolean validInput = false;
		String estudianteID = "";
		String learningPathID = "";

		while (!validInput) {
			System.out.println("\n");
			System.out.print("ID del Estudiante: ");
			estudianteID = scanner.nextLine();

			System.out.print("ID del Learning Path: ");
			learningPathID = scanner.nextLine();

			LearningPath lp = null;
			for (LearningPath learningPath : learningPaths) {
				if (learningPath.getLearningPathID().equals(learningPathID+"_"+estudianteID)) {
					lp = learningPath;
					break;
				}
			}

			Estudiante estudiante = null;
			for (Estudiante est : estudiantes) {
				if (est.getUsuarioID().equals(estudianteID)) {
					estudiante = est;
					break;
				}
			}

			if (lp != null && estudiante != null) {
				validInput = true;
				Map<String, String> progreso = profesorActual.verProgresoEstudiante(estudianteID, learningPathID+"_"+estudianteID);
				System.out.println("Progreso del Estudiante: " + progreso);
			} else {
				System.out.println("Learning Path o Estudiante no encontrados. Por favor, intente de nuevo.");
			}
		}
	}

	private static void revisarFeedback() {
		boolean validInput = false;
		String learningPathID = "";

		while (!validInput) {
			System.out.println("\n");
			System.out.print("ID del Learning Path: ");
			learningPathID = scanner.nextLine();

			LearningPath lp = null;
			for (LearningPath learningPath : learningPaths) {
				if (learningPath.getLearningPathID().equals(learningPathID)) {
					lp = learningPath;
					break;
				}
			}

			if (lp != null) {
				validInput = true;
				List<Map> feedbacks = profesorActual.revisarFeedback(learningPathID);
				if (feedbacks == null || feedbacks.isEmpty()) {
					throw new RuntimeException("No feedback found for the given Learning Path ID.");
				}
				feedbacks.forEach(System.out::println);
			} else {
				System.out.println("Learning Path not found. Please try again.");
			}
		}
	}

	private static void calcularRating() {
		boolean validInput = false;
		String learningPathID = "";

		while (!validInput) {
			System.out.println("\n");
			System.out.print("ID del Learning Path: ");
			learningPathID = scanner.nextLine();

			LearningPath lp = null;
			for (LearningPath learningPath : learningPaths) {
				if (learningPath.getLearningPathID().equals(learningPathID)) {
					lp = learningPath;
					break;
				}
			}

			if (lp != null) {
				validInput = true;
				double rating = profesorActual.calcularRating(learningPathID);
				System.out.println("Rating promedio: " + rating);
			} else {
				System.out.println("Learning Path no encontrado. Por favor, intente de nuevo.");
			}
		}
	}

    private static void persistData() {
    	
        persistenciaUsuarios.salvarProfesor(usuariosFile, profesorActual.getUsuarioID(), profesorActual.getNombre(), profesorActual.getContraseña(), profesorActual.getEmail(), profesorActual.getTipoUsuario());
    }
    
    
}