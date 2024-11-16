package Consola;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import Usuarios.Profesor;
import LearningPaths.LearningPath;
import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Perisistencia.PersistenciaUsuarios;
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaLearningPaths;
import Actividades.Pregunta;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import Exceptions.ActividadNoPertenece;
import Exceptions.LearningPathNoInscrito;
import Exceptions.NombreRepetido;
import Exceptions.YaSeCompleto;

public class ConsolaProfesor {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Profesor profesorActual;
    private static PersistenciaUsuarios persistenciaUsuarios = new PersistenciaUsuarios();
    private static PersistenciaActividades persistenciaActividades = new PersistenciaActividades();
    private static PersistenciaLearningPaths persistenciaLearningPaths = new PersistenciaLearningPaths();
    private static final String usuariosFile = "src/datos/users.json";
    private static final String actividadesFile = "src/datos/activities.json";
    private static final String learningPathsFile = "src/datos/learning_paths.json";
    
    
    public static void main(String[] args) throws NombreRepetido, LearningPathNoInscrito, ActividadNoPertenece, YaSeCompleto {
        cargarProfesores();
        boolean authenticated = false;

        if (iniciarSesion() == 1) {
            while (!authenticated) {
                if (authenticar()) {
                    authenticated = true;
                    menu();
                } else {
                    System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
                }
            }
        } else {
            boolean registered = false;
            while (!registered) {
                registrarse();
                System.out.println("Usuario registrado");
                System.out.println("Iniciar sesión");
                if (authenticar()) {
                    registered = true;
                    menu();
                    
                } else {
                    System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
                }
            }
        }
    }

    private static void cargarProfesores() {
        List<Profesor> profesores;
		try {
			profesores = persistenciaUsuarios.cargarProfesores(usuariosFile);
            System.out.println("Informacion cargada" );
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
    
    private static int iniciarSesion() {
        System.out.println("1. Iniciar Sesion");
        System.out.println("2. Registrarse");
        System.out.print("Seleccione una opción: ");
        int opcion = scanner.nextInt();
        scanner.nextLine(); 
        return opcion;
    }

    private static boolean authenticar() {
        System.out.print("UsuarioID: ");
        String usuarioID = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        
        List<Profesor> profesores;
		try {
			profesores = persistenciaUsuarios.cargarProfesores(usuariosFile);
			for (Profesor profesor : profesores) {
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
    
    public static void registrarse() {
    	
    	
    	System.out.print("Ingrese su usuario ID: ");
        String usuarioID = scanner.nextLine();
        System.out.print("Ingrese su nombre: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese su contraseña: ");
        String contrasena = scanner.nextLine();
        System.out.print("Ingrese su email: ");
        String email = scanner.nextLine();
        
    	Profesor profesor = new Profesor(usuarioID, nombre, contrasena, email, "Profesor");
    	
    	persistenciaUsuarios.salvarProfesor(usuariosFile, profesor.getUsuarioID(), profesor.getNombre(),
				profesor.getContraseña(), profesor.getEmail(), profesor.getTipoUsuario());	
    }

    private static void menu() throws NombreRepetido {
        int option;
        do {
            System.out.println("---- Profesor Interface ----");
            System.out.println("1. Crear Actividad");
            System.out.println("2. Crear Learning Path");
            System.out.println("3. Revisar Estado de Actividad");
            System.out.println("4. Ver Progreso de Estudiante");
            System.out.println("5. Revisar Feedback");
            System.out.println("6. Calcular Rating");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine(); 
            handleOption(option);
        } while (option != 7);
    }

    private static void handleOption(int option) throws NombreRepetido {
        switch (option) {
            case 1 -> crearActividad();
            case 2 -> crearLearningPath();
            case 3 -> revisarEstadoActividad();
            case 4 -> verProgresoEstudiante();
            case 5 -> revisarFeedback();
            case 6 -> calcularRating();
            case 7 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción inválida.");
        }
    }


private static void crearActividad() throws NombreRepetido {
    System.out.println("Ingrese el tipo de actividad");
    System.out.println("1. Encuesta");
    System.out.println("2. Examen");
    System.out.println("3. Quiz");
    System.out.println("4. Recurso educativo");
    System.out.println("5. Tarea");
    System.out.print("Seleccione una opción: ");
    int opcion = scanner.nextInt();
    scanner.nextLine(); // Consume newline

    if (opcion == 1) {
        System.out.print("ID de la Actividad: ");
        String actividadID = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Objetivo: ");
        String objetivo = scanner.nextLine();
        System.out.print("Nivel de dificultad: ");
        int nivelDificultad = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Duración esperada: ");
        int duracionEsperada = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Es obligatoria: ");
        boolean esObligatoria = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        System.out.print("Fecha límite: ");
        String fechaLimite = scanner.nextLine();

        Date fecha = new Date();
        try {
            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimite);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("Reseñas: ");
        String resenas = scanner.nextLine();
        System.out.print("Calificación: ");
        int calificacion = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Resultado: ");
        double resultado = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Actividades previas: ");
        List<String> actividadesPrevias = new ArrayList<>();
        String actividadPrevia = scanner.nextLine();
        actividadesPrevias.add(actividadPrevia);

        System.out.print("Actividades de seguimiento: ");
        List<String> actividadesSeguimiento = new ArrayList<>();
        String actividadSeguimiento = scanner.nextLine();
        actividadesSeguimiento.add(actividadSeguimiento);

        System.out.print("Preguntas: ");
        List<String> preguntas = new ArrayList<>();
        String pregunta = scanner.nextLine();
        preguntas.add(pregunta);
        
        System.out.println("Ingrese el ID del learning Path al que quiere que pertenezca la actividad: ");
        String learningPathID = scanner.nextLine();
        String tipo = "Encuesta";
        
        HashMap<String, Object>parametrosEspecificos = new HashMap<>();
        parametrosEspecificos.put("Preguntas", preguntas);

        Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
                 duracionEsperada,  esObligatoria,  fecha,  resenas,
                 resultado,  calificacion,  tipo,  learningPathID, actividadesPrevias, actividadesSeguimiento,
                parametrosEspecificos, actividadPrevia);

        persistenciaActividades.salvarActividad(actividadesFile, actividad);

    } else if (opcion == 2) {
        System.out.print("ID de la Actividad: ");
        String actividadID = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Objetivo: ");
        String objetivo = scanner.nextLine();
        System.out.print("Nivel de dificultad: ");
        int nivelDificultad = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Duración esperada: ");
        int duracionEsperada = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Es obligatoria: ");
        boolean esObligatoria = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        System.out.print("Fecha límite: ");
        String fechaLimite = scanner.nextLine();

        Date fecha = new Date();
        try {
            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimite);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("Reseñas: ");
        String resenas = scanner.nextLine();
        System.out.print("Calificación: ");
        double calificacion = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Resultado: ");
        double resultado = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Actividades previas: ");
        List<String> actividadesPrevias = new ArrayList<>();
        String actividadPrevia = scanner.nextLine();
        actividadesPrevias.add(actividadPrevia);

        System.out.print("Actividades de seguimiento: ");
        List<String> actividadesSeguimiento = new ArrayList<>();
        String actividadSeguimiento = scanner.nextLine();
        actividadesSeguimiento.add(actividadSeguimiento);

        System.out.print("Preguntas: ");
        List<Pregunta> preguntas = new ArrayList<>();
        String pregunta = scanner.nextLine();
        List<String> opciones = new ArrayList<>();
        System.out.print("Opción 1: ");
        String opcion1 = scanner.nextLine();
        opciones.add(opcion1);
        preguntas.add(new Pregunta(pregunta, opciones));

        System.out.print("Calificación mínima: ");
        double calificacionMinima = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        Actividad actividad = new Examen(actividadID, descripcion, objetivo, nivelDificultad, 
                duracionEsperada, esObligatoria, fecha, resenas, calificacion, resultado, 
                actividadesPrevias, actividadesSeguimiento, preguntas, calificacionMinima);

        persistenciaActividades.salvarActividad(actividadesFile, actividad);

    } else if (opcion == 3) {
        System.out.print("ID de la Actividad: ");
        String actividadID = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Objetivo: ");
        String objetivo = scanner.nextLine();
        System.out.print("Nivel de dificultad: ");
        int nivelDificultad = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Duración esperada: ");
        int duracionEsperada = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Es obligatoria: ");
        boolean esObligatoria = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        System.out.print("Fecha límite: ");
        String fechaLimite = scanner.nextLine();

        Date fecha = new Date();
        try {
            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimite);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("Reseñas: ");
        String resenas = scanner.nextLine();
        System.out.print("Calificación: ");
        double calificacion = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Resultado: ");
        double resultado = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Actividades previas: ");
        List<String> actividadesPrevias = new ArrayList<>();
        String actividadPrevia = scanner.nextLine();
        actividadesPrevias.add(actividadPrevia);

        System.out.print("Actividades de seguimiento: ");
        List<String> actividadesSeguimiento = new ArrayList<>();
        String actividadSeguimiento = scanner.nextLine();
        actividadesSeguimiento.add(actividadSeguimiento);

        System.out.print("Preguntas: ");
        List<Pregunta> preguntas = new ArrayList<>();
        String pregunta = scanner.nextLine();
        List<String> opciones = new ArrayList<>();
        System.out.print("Opción 1: ");
        String opcion1 = scanner.nextLine();
        opciones.add(opcion1);
        preguntas.add(new Pregunta(pregunta, opciones));

        System.out.print("Calificación mínima: ");
        double calificacionMinima = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Respuesta correcta: ");
        String respuestaCorrecta = scanner.nextLine();

        Actividad actividad = new Quiz(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                esObligatoria, fecha, resenas, calificacion, resultado, actividadesPrevias, actividadesSeguimiento,
                preguntas, calificacionMinima, respuestaCorrecta);

        persistenciaActividades.salvarActividad(actividadesFile, actividad);

    } else if (opcion == 4) {
        System.out.print("ID de la Actividad: ");
        String actividadID = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Objetivo: ");
        String objetivo = scanner.nextLine();
        System.out.print("Nivel de dificultad: ");
        int nivelDificultad = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Duración esperada: ");
        int duracionEsperada = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Es obligatoria: ");
        boolean esObligatoria = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        System.out.print("Fecha límite: ");
        String fechaLimite = scanner.nextLine();

        Date fecha = new Date();
        try {
            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimite);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("Reseñas: ");
        String resenas = scanner.nextLine();
        System.out.print("Calificación: ");
        double calificacion = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Resultado: ");
        double resultado = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Actividades previas: ");
        List<String> actividadesPrevias = new ArrayList<>();
        String actividadPrevia = scanner.nextLine();
        actividadesPrevias.add(actividadPrevia);

        System.out.print("Actividades de seguimiento: ");
        List<String> actividadesSeguimiento = new ArrayList<>();
        String actividadSeguimiento = scanner.nextLine();
        actividadesSeguimiento.add(actividadSeguimiento);

        System.out.print("Tipo de recurso: ");
        String tipoRecurso = scanner.nextLine();
        System.out.print("Link del recurso: ");
        String linkRecurso = scanner.nextLine();

        Actividad actividad = new RecursoEducativo(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                esObligatoria, fecha, resenas, calificacion, resultado, actividadesPrevias, actividadesSeguimiento,
                tipoRecurso, linkRecurso);

        persistenciaActividades.salvarActividad(actividadesFile, actividad);

    } else if (opcion == 5) {
        System.out.print("ID de la Actividad: ");
        String actividadID = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Objetivo: ");
        String objetivo = scanner.nextLine();
        System.out.print("Nivel de dificultad: ");
        int nivelDificultad = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Duración esperada: ");
        int duracionEsperada = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Es obligatoria: ");
        boolean esObligatoria = scanner.nextBoolean();
        scanner.nextLine(); // Consume newline

        System.out.print("Fecha límite: ");
        String fechaLimite = scanner.nextLine();

        Date fecha = new Date();
        try {
            fecha = new SimpleDateFormat("dd/MM/yyyy").parse(fechaLimite);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print("Reseñas: ");
        String resenas = scanner.nextLine();
        System.out.print("Calificación: ");
        double calificacion = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Resultado: ");
        double resultado = scanner.nextDouble();
        scanner.nextLine(); // Consume newline

        System.out.print("Actividades previas: ");
        List<String> actividadesPrevias = new ArrayList<>();
        String actividadPrevia = scanner.nextLine();
        actividadesPrevias.add(actividadPrevia);

        System.out.print("Actividades de seguimiento: ");
        List<String> actividadesSeguimiento = new ArrayList<>();
        String actividadSeguimiento = scanner.nextLine();
        actividadesSeguimiento.add(actividadSeguimiento);

        System.out.print("Preguntas: ");
        List<Pregunta> preguntas = new ArrayList<>();
        String pregunta = scanner.nextLine();
        List<String> opciones = new ArrayList<>();
        System.out.print("Opción 1: ");
        String opcion1 = scanner.nextLine();
        opciones.add(opcion1);
        preguntas.add(new Pregunta(pregunta, opciones));

        System.out.print("Instrucciones: ");
        String instrucciones = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();

        Actividad actividad = new Tarea(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada,
                esObligatoria, fecha, resenas, calificacion, resultado, actividadesPrevias, actividadesSeguimiento,
                preguntas, instrucciones, estado);

        persistenciaActividades.salvarActividad(actividadesFile, actividad);
    }
}

    


private static void crearLearningPath() {
    System.out.print("Ingrese el id del Learning Path: ");
    String LearningPathID = scanner.nextLine();
    System.out.print("Ingrese el titulo del Learning Path: ");
    String titulo = scanner.nextLine();
    System.out.print("Ingrese la descripción del Learning Path: ");
    String descripcion = scanner.nextLine();
    System.out.print("Ingrese los objetivos del Learning Path: ");
    String objetivos = scanner.nextLine();
    System.out.print("Ingrese el nivel de dificultad del Learning Path: ");
    int nivelDificultad = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    System.out.print("Ingrese la duración del Learning Path: ");
    int duracion = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    System.out.print("Ingrese el id del profesor: ");
    String profesorID = scanner.nextLine();

    List<String> actividadesID = new ArrayList<>();
    System.out.print("Ingrese las actividades del Learning Path: ");
    String actividadID = scanner.nextLine();
    actividadesID.add(actividadID);

    List<String> intereses = new ArrayList<>();
    System.out.print("Ingrese los intereses del Learning Path: ");
    String interes = scanner.nextLine();
    intereses.add(interes);

    persistenciaLearningPaths.salvarLearningPath(learningPathsFile, LearningPathID,
            titulo, descripcion, objetivos, nivelDificultad, duracion, profesorID,
            actividadesID, intereses);
}


    private static void revisarEstadoActividad() {
        System.out.print("ID de la Actividad: ");
        String actividadID = scanner.nextLine();
        System.out.print("ID del Learning Path: ");
        String learningPathID = scanner.nextLine();
        profesorActual.revisarEstadoActividad(actividadID, learningPathID);
    }

    private static void verProgresoEstudiante() {
        System.out.print("ID del Estudiante: ");
        String estudianteID = scanner.nextLine();
        System.out.print("ID del Learning Path: ");
        String learningPathID = scanner.nextLine();
        Map<String, String> progreso = profesorActual.verProgresoEstudiante(estudianteID, learningPathID);
        System.out.println("Progreso del Estudiante: " + progreso);
    }

    private static void revisarFeedback() {
        System.out.print("ID del Learning Path: ");
        String learningPathID = scanner.nextLine();
        List<Map> feedbacks = profesorActual.revisarFeedback(learningPathID);
        feedbacks.forEach(System.out::println);
    }

    private static void calcularRating() {
        System.out.print("ID del Learning Path: ");
        String learningPathID = scanner.nextLine();
        double rating = profesorActual.calcularRating(learningPathID);
        System.out.println("Rating promedio: " + rating);
    }

    private static void persistData() {
        persistenciaUsuarios.salvarProfesor(usuariosFile, profesorActual.getUsuarioID(), profesorActual.getNombre(), profesorActual.getContraseña(), profesorActual.getEmail(), profesorActual.getTipoUsuario());
    }
    
    
}