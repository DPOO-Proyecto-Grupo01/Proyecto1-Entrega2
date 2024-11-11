package Consola;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Actividades.Actividad;
import Actividades.Pregunta;
import Exceptions.NombreRepetido;
import LearningPaths.LearningPath;
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaLearningPaths;
import Perisistencia.PersistenciaUsuarios;
import Usuarios.Estudiante;
import Usuarios.Profesor;

public class ConsolaEstudiante {
	
    private static Scanner scanner = new Scanner(System.in);
    private static Estudiante estudianteActual;
    private static PersistenciaUsuarios persistenciaUsuarios = new PersistenciaUsuarios();
    private static PersistenciaActividades persistenciaActividades = new PersistenciaActividades();
    private static PersistenciaLearningPaths persistenciaLearningPaths = new PersistenciaLearningPaths();
    private static final String usuariosFile = "src/datos/users.json";
    private static final String actividadesFile = "src/datos/activities.json";
    private static final String learningPathsFile = "src/datos/learning_paths.json";
    

	private static void cargarEstudiantes() {
        List<Estudiante> estudiantes;
		try {
			estudiantes = persistenciaUsuarios.cargarEstudiantes(usuariosFile);
			System.out.println("Datos de estudiantes cargados correctamente");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
    }
	
    private static void menu1() throws NombreRepetido {
        int option;
        do {
        	System.out.println("Desea registrarse o iniciar sesión: ");
        	System.out.println("1. Registrarme");
            System.out.println("2. Iniciar sesión");
            option = scanner.nextInt();
            scanner.nextLine(); 
            handleOption(option);
        } while (option != 2);
    }
    
    private static void handleOption(int option) throws NombreRepetido {
        switch (option) {
            case 1 -> registrarse();
            case 2 -> IniciarSesión();
            default -> System.out.println("Opción inválida.");
        }
   
    
    
    public static registrarse() {
    	System.out.println("Ingrese un correo electronico: ")
    	String email = scanner.nextLine();
    	
		System.out.print("UsuarioID: ");
		String usuarioID = scanner.nextLine();
		System.out.print("Nombre: ");
		String nombre = scanner.nextLine();
		System.out.print("Contraseña: ");
		String contrasena = scanner.nextLine();
		System.out.print("Email: ");
		String email = scanner.nextLine();
		System.out.print("Intereses: ");
		String intereses = scanner.nextLine();

		Estudiante estudiante = new Estudiante(usuarioID, nombre, contrasena, email, "Estudiante");
		estudiante.setIntereses(intereses);
		persistenciaUsuarios.salvarEstudiante(usuariosFile, estudiante.getUsuarioID(), estudiante.getNombre(),
				estudiante.getContraseña(), estudiante.getEmail(), estudiante.getTipoUsuario(),
				estudiante.getIntereses());
	}
    }
	
    public static void main(String[] args) {
        cargarEstudiantes(); 
        if (authenticar()) {
            menu();
        } else {
            System.out.println("Usiaro o contraseña incorrectos.");
        }
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

    private static void menu2() throws NombreRepetido {
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

    private static void handleOption2(int option) throws NombreRepetido {
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

    private static void persistData() {
        persistenciaUsuarios.salvarEstudiante(usuariosFile, estudianteActual.getUsuarioID(), estudianteActual.getNombre(), estudianteActual.getContraseña(), estudianteActual.getEmail(), estudianteActual.getTipoUsuario());
    }
    
}
