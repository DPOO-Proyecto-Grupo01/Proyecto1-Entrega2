package Consola;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import Usuarios.Profesor;
import LearningPaths.LearningPath;
import Actividades.Actividad;
import Perisistencia.PersistenciaUsuarios;
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaLearningPaths;
import Actividades.Pregunta;
import Exceptions.NombreRepetido;

public class ConsolaProfesor {
    
    private static Scanner scanner = new Scanner(System.in);
    private static Profesor profesorActual;
    private static PersistenciaUsuarios persistenciaUsuarios = new PersistenciaUsuarios();
    private static PersistenciaActividades persistenciaActividades = new PersistenciaActividades();
    private static PersistenciaLearningPaths persistenciaLearningPaths = new PersistenciaLearningPaths();
    private static final String usuariosFile = "src/datos/users.json";
    private static final String actividadesFile = "src/datos/activities.json";
    private static final String learningPathsFile = "src/datos/learning_paths.json";
    
    public static void main(String[] args) {
        cargarProfesores(); 
        if (iniciarSesion() == 1) {
	        if (authenticar()) {
	            menu();
	        } else {
	            System.out.println("Usuario o contraseña incorrectos.");
	        }
		} else {
			
			//registrarse();
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

    private static void menu() {
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

    private static void handleOption(int option) {
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

    private static void crearActividad() {
        System.out.print("Ingrese el ID de la Actividad: ");
        String actividadID = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
        System.out.print("Objetivo: ");
        String objetivo = scanner.nextLine();
        System.out.print("Nivel de Dificultad: ");
        int nivelDificultad = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Duración Esperada (min): ");
        int duracionEsperada = scanner.nextInt();
        scanner.nextLine(); 

        System.out.print("Es Obligatoria (true/false): ");
        boolean esObligatoria = scanner.nextBoolean();
        scanner.nextLine(); 

        System.out.print("Ingrese el tipo de actividad (Quiz/Examen/Encuesta/Recurso Educativo/Tarea): ");
        String tipo = scanner.nextLine();
        
        HashMap<String, Object> parametrosEspecificos = new HashMap<>();
        if (tipo.equals("Quiz") || tipo.equals("Examen")) {
            System.out.print("Calificación mínima: ");
            parametrosEspecificos.put("calificacionMinima", scanner.nextDouble());
            scanner.nextLine();
        }
        
        List<Pregunta> preguntas = new ArrayList<>();
        System.out.print("Número de preguntas: ");
        int numPreguntas = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < numPreguntas; i++) {
            System.out.print("Pregunta " + (i+1) + ": ");
            String preguntaText = scanner.nextLine();
            // You can ask for options here
            List<String> opciones = new ArrayList<>();
            preguntas.add(new Pregunta(preguntaText, opciones));
        }
        parametrosEspecificos.put("preguntas", preguntas);

        Actividad actividad;
		try {
			actividad = profesorActual.crearActividad(
			    actividadID, descripcion, objetivo, nivelDificultad,
			    duracionEsperada, esObligatoria, new Date(), "", 0, 0,
			    tipo, "LearningPathID", null, null, parametrosEspecificos, "");
				System.out.println("Actividad creada con éxito: " + actividad.getDescripcion());
		} catch (NombreRepetido e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        persistData();
    }

    private static void crearLearningPath() {
        System.out.print("ID de Learning Path: ");
        String learningPathID = scanner.nextLine();
        System.out.print("Título: ");
        String titulo = scanner.nextLine();
        System.out.print("Descripción: ");
        String descripcion = scanner.nextLine();
       

        LearningPath learningPath;
		try {
			learningPath = profesorActual.crearLearningPath(
			    learningPathID, titulo, descripcion, "Objetivos",
			    3, 120, profesorActual.getUsuarioID(), null, null);
				System.out.println("Learning Path creado: " + learningPath.getTitulo());
		} catch (NombreRepetido e) {
			
			e.printStackTrace();
			
		}
        
        persistData();
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
