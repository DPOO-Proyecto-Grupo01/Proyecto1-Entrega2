package Consola;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Actividades.Actividad;
import Actividades.Pregunta;
import Exceptions.ActividadNoPertenece;
import Exceptions.LearningPathNoInscrito;
import Exceptions.NombreRepetido;
import Exceptions.YaSeCompleto;
import LearningPaths.LearningPath;
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaLearningPaths;
import Perisistencia.PersistenciaUsuarios;
import Usuarios.Estudiante;
import Usuarios.Profesor;
import Usuarios.Usuario;

public class ConsolaEstudiante {
	
    private static Scanner scanner = new Scanner(System.in);
    private static Estudiante estudianteActual;
    private static PersistenciaUsuarios persistenciaUsuarios = new PersistenciaUsuarios();
    private static PersistenciaActividades persistenciaActividades = new PersistenciaActividades();
    private static PersistenciaLearningPaths persistenciaLearningPaths = new PersistenciaLearningPaths();
    private static final String usuariosFile = "src/datos/users.json";
    private static final String actividadesFile = "src/datos/activities.json";
    private static final String learningPathsFile = "src/datos/learning_paths.json";
    private static List<LearningPath> learningPaths;
    private static Map<String, Profesor> profesores = new HashMap<>();
    private static List<Actividad> actividades;
    
    private static int contador = 1;
    
    public static void main(String[] args) throws NombreRepetido, LearningPathNoInscrito, ActividadNoPertenece, YaSeCompleto {
    	cargarActividades();
    	cargarLearningPaths();
    	cargarProfesores();
    	cargarLpProfesoress();
    	cargarActividadesLP();
        cargarEstudiantes();
        for (Profesor profesor : profesores.values()) {
            System.out.println("el profesor: "+ profesor.getUsuarioID()+"Tiene estos lps: " + profesor.getLearningPathsCreados().keySet());
        }
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


	private static void cargarEstudiantes() {
        List<Estudiante> estudiantes;
		try {
			estudiantes = persistenciaUsuarios.cargarEstudiantes(usuariosFile);
			System.out.println("Datos de estudiantes cargados correctamente");
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
	
	
	public static void cargarProfesores() {
		List<Profesor> profesoresLista;
		try {
			profesoresLista = persistenciaUsuarios.cargarProfesores(usuariosFile);
			for (Profesor profesor : profesoresLista) {
				profesores.put(profesor.getUsuarioID(), profesor);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cargarActividades() {
		try {
			actividades = persistenciaActividades.cargarActividades(actividadesFile);
			System.out.println("Actividades cargadas correctamente"+ actividades.size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cargarLpProfesoress() {
		try {
			persistenciaUsuarios.cargarLearningPathsProfesor(learningPaths, profesores);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cargarActividadesLP() {
		try {
			persistenciaLearningPaths.cargarActividadesDelLearningPath(actividades,learningPaths);
			
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
	
    private static void menu() throws NombreRepetido, LearningPathNoInscrito, ActividadNoPertenece, YaSeCompleto {
        int option;
        do {
            System.out.println("---- Estudiante Interface ----");
            System.out.println("1. Inscribirse a un Learning Path");
            System.out.println("2. Completar actividad");
            System.out.println("3. Ver progreso de un learningPath");
            System.out.println("4. Ver actividades por completar");
            System.out.println("5. Enviar Feedback");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");
            option = scanner.nextInt();
            scanner.nextLine(); 
            handleOption(option);
        } while (option != 7);
    }

    private static void handleOption(int option) throws NombreRepetido, LearningPathNoInscrito, ActividadNoPertenece, YaSeCompleto {
        switch (option) {
            case 1 -> mostrarRecomendacionesYInscribirLearningPath();
            case 2 -> completarActividad();
            case 3 -> verProgresoLearningPath();
            case 4 -> verActividadesPorCompletar();
            case 5 -> enviarFeedback();
            case 6 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción inválida.");
        }

	}
	
    
   
   
    private static void registrarse() throws NombreRepetido {
    	System.out.println("Ingrese un correo electronico: ");
    	String email = scanner.nextLine();
    	
		String usuarioID=  "E"+ Integer.toString(contador);
		contador+=1;
		
		System.out.print("Ingrese su nombre: ");
		String nombre = scanner.nextLine();
		
		System.out.print("Ingrese su contraseña: ");
		String contrasena = scanner.nextLine();

		System.out.print("Ingrese sus intereses: ");
		String intereses = scanner.nextLine();

		Estudiante estudiante = new Estudiante(usuarioID, nombre, contrasena, email, "Estudiante");
		estudiante.setIntereses(intereses);
		persistenciaUsuarios.salvarEstudiante(usuariosFile, estudiante.getUsuarioID(), estudiante.getNombre(),
				estudiante.getContraseña(), estudiante.getEmail(), estudiante.getTipoUsuario());
		
	}
        
        
	private static boolean authenticar() {
        System.out.print("UsuarioID: ");
        String usuarioID = scanner.nextLine();
        System.out.print("Contraseña: ");
        String contrasena = scanner.nextLine();
        
        List<Estudiante> estudiantes;
		try {
			estudiantes = persistenciaUsuarios.cargarEstudiantes(usuariosFile);
			for (Estudiante estudiante : estudiantes) {
	            if (estudiante.getUsuarioID().equals(usuarioID) && estudiante.getContraseña().equals(contrasena)) {
	                estudianteActual = estudiante;
	                return true;
	            }
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
     
        return false;
    }

    
	private static void mostrarRecomendacionesYInscribirLearningPath() throws LearningPathNoInscrito {
		
		//Imprimir la lista de profesores no repetidos

		try {
			System.out.println("Lista de profesores: ");
			for (Profesor profesor : profesores.values()) {
				System.out.println(profesor.getUsuarioID());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.print("Ingrese el ID del profesor al que desea inscribir: ");
        String profesorID = scanner.nextLine();
       
        
		for (String profesor : profesores.keySet()) {
			if (profesor.equals(profesorID)) {
				System.out.println("Profesor encontrado");
				estudianteActual.inscribirProfesor(profesores.get(profesor), profesorID);
				break;
			}
		}

        System.out.print("Ingrese su intereses academicos: ");
        String intereses = scanner.nextLine();
        estudianteActual.setIntereses(intereses);
		String recomendaciones = estudianteActual.obtenerRecomendacion(intereses, profesorID);
		System.out.println(recomendaciones);
		
		System.out.print("Ingrese el ID del Learning Path al que desea inscribirse: ");
		String learningPathID = scanner.nextLine();

		estudianteActual.inscribirLearningPath(learningPathID, profesorID);
	}

	private static void completarActividad() throws ActividadNoPertenece, YaSeCompleto {
		System.out.print("Ingrese el ID del Learning Path en el que desea trabajar: ");
		String learningPathID = scanner.nextLine();
		System.out.print("Ingrese el ID de la actividad que desea completar: ");
		String actividadID = scanner.nextLine();
		
		estudianteActual.completarActividad( actividadID, learningPathID);
	}

	private static void verProgresoLearningPath() throws LearningPathNoInscrito {
		System.out.print("Ingrese el ID del Learning Path que desea revisar: ");
		String learningPathID = scanner.nextLine();
		System.out.println(estudianteActual.getProgresoLearningPath(learningPathID));
	}
		
	private static void verActividadesPorCompletar() throws LearningPathNoInscrito {
		System.out.print("Ingrese el ID del Learning Path que desea revisar: ");
		String learningPathID = scanner.nextLine();
		System.out.println(estudianteActual.actividadesDisponibles(learningPathID));
	}

	private static void enviarFeedback() throws LearningPathNoInscrito {
		System.out.print("Ingrese el ID del Learning Path que desea revisar: ");
		String learningPathID = scanner.nextLine();
		System.out.println("Ingrese su feedback: ");
		String feedback = scanner.nextLine();
		System.out.println("Ingrese su calificación: ");
		int calificacion = scanner.nextInt();
		String feedbackID = "F"+ Integer.toString(contador);
		
		estudianteActual.enviarFeedback(learningPathID, feedback, calificacion, feedbackID);
	}

	private static void persistData() {
        persistenciaUsuarios.salvarEstudiante(usuariosFile, estudianteActual.getUsuarioID(), estudianteActual.getNombre(), estudianteActual.getContraseña(), estudianteActual.getEmail(), estudianteActual.getTipoUsuario());
    }
}