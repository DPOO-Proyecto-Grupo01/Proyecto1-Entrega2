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
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;
import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Perisistencia.PersistenciaUsuarios;
import Perisistencia.PersistenciaActividades;
import Perisistencia.PersistenciaFeedback;
import Perisistencia.PersistenciaLearningPaths;
import Perisistencia.PersistenciaProgreso;
import Actividades.Pregunta;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import Exceptions.NombreRepetido;

public class ConsolaProfesor {
    
    private static Scanner scanner = new Scanner(System.in);
    public static Profesor profesorActual;
    private static PersistenciaUsuarios persistenciaUsuarios = new PersistenciaUsuarios();
    private static PersistenciaActividades persistenciaActividades = new PersistenciaActividades();
    private static PersistenciaLearningPaths persistenciaLearningPaths = new PersistenciaLearningPaths();
    private static PersistenciaProgreso persistenciaProgreso = new PersistenciaProgreso();
    private static PersistenciaFeedback persistenciaFeedback = new PersistenciaFeedback();
    private static final String usuariosFile = "src/datos/users.json";
    private static final String actividadesFile = "src/datos/activities.json";
    private static final String learningPathsFile = "src/datos/learning_paths.json";
    private static final String progresoFile = "src/datos/progreso.json";
    private static final String feedbackFile = "src/datos/feedback.json";
    private static List<LearningPath> learningPaths;
    private static Map<String, Profesor> profesores = new HashMap<>();
    private static List<Actividad> actividades;
    private static List<Estudiante> estudiantes;
    private static List<Profesor> profesoresLista;
    private static List<Progreso> progresos;
    private static List<Feedback> feedbacks;
    
    
    public static void main(String[] args) throws Exception { 
    	cargarActividades();
        cargarLearningPaths();
        cargarProgresos();
        cargarProgresoLP();
        cargarFeedback();
        cargarFeedbackLP();
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
	
	
	
	public static void cargarFeedback() {
        try {
            feedbacks = persistenciaFeedback.cargarFeedback(feedbackFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	public static void cargarFeedbackLP() {
        try {
            persistenciaLearningPaths.cargarFeedbackDelLearningPath(feedbacks, learningPaths);
        } catch (Exception e) {
            e.printStackTrace();
        }}
	
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
            case 1 -> crearActividad(null, null, null, option, option, false, null, null, option, option, null, null, null, null, null, null);
            case 2 -> crearLearningPath(null, null, null, null, option, option, null, null, null);
            case 3 -> revisarEstadoActividad(null, null);
            case 4 -> verProgresoEstudiante(null, null);
            case 5 -> revisarFeedback(null);
            case 6 -> calcularRating(null);
            case 7 -> System.out.println("Saliendo...");
            default -> System.out.println("Opción inválida.");
        }
    }


    public static void crearActividad(String actividadID, String descripcion, String objetivo, int nivelDificultad,
            int duracionEsperada, boolean esObligatoria, Date fechaLimite, String resenas,
            double resultado, int calificacion, String tipo, String learningPathID, List<String> actividadesPrevia, 
            List<String> actividadesSeguimiento,
            HashMap<String, Object> parametrosEspecificos, String actividadPrevia) throws Exception {
        System.out.println("Ingrese el tipo de actividad");
        System.out.println("1. Encuesta");
        System.out.println("2. Examen");
        System.out.println("3. Quiz");
        System.out.println("4. Recurso educativo");
        System.out.println("5. Tarea");
        System.out.println("\n");
        System.out.print("Seleccione una opción: ");
        

        if (tipo.equals("Encuesta")) {
        	
		Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
		        duracionEsperada, esObligatoria, fechaLimite, resenas,
		        resultado, calificacion, tipo, learningPathID, actividadesPrevia, actividadesSeguimiento,
		        parametrosEspecificos, actividadPrevia);
		
		persistenciaActividades.salvarActividad(actividadesFile, actividad);

		} else if (tipo.equals("Examen")) {
		    
		Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
		        duracionEsperada, esObligatoria, fechaLimite, resenas,
		        resultado, calificacion, tipo, learningPathID, actividadesPrevia, actividadesSeguimiento,
		        parametrosEspecificos, actividadPrevia);
		
		persistenciaActividades.salvarActividad(actividadesFile, actividad);
		
		} else if (tipo.equals("Quiz")) {
		   		
		Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
		        duracionEsperada, esObligatoria, fechaLimite, resenas,
		        resultado, calificacion, tipo, learningPathID, actividadesPrevia, actividadesSeguimiento,
		        parametrosEspecificos, actividadPrevia);
		
		persistenciaActividades.salvarActividad(actividadesFile, actividad);
		
		} else if (tipo.equals("Recurso Educativo")) {
		   
		Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
		        duracionEsperada, esObligatoria, fechaLimite, resenas,
		        resultado, calificacion, tipo, learningPathID, actividadesPrevia, actividadesSeguimiento,
		        parametrosEspecificos, actividadPrevia);
		
		persistenciaActividades.salvarActividad(actividadesFile, actividad);
		
		} else if (tipo.equals("Tarea")) {
		   
            Actividad actividad = profesorActual.crearActividad(actividadID, descripcion, objetivo, nivelDificultad,
                     duracionEsperada, esObligatoria, fechaLimite, resenas,
                     resultado, calificacion, tipo, learningPathID, actividadesPrevia, actividadesSeguimiento,
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
 




	public static String revisarEstadoActividad(String actividadID, String learningPathID) {
		boolean validInput = false;
		
	
		while (!validInput) {
			
			LearningPath lp = null;
			for (LearningPath learningPath : learningPaths) {
				if (learningPath.getLearningPathID().equals(learningPathID)) {
					lp = learningPath;
					break;
				}
			}
	
			if (lp != null && lp.getActividades().containsKey(actividadID)) {
				validInput = true;
				return profesorActual.revisarEstadoActividad(actividadID, learningPathID);
			} else {
				System.out.println("Learning Path o Actividad no encontrados. Por favor, intente de nuevo.");
			}
		}
		return "No se encontro estado";
	}

	public static String verProgresoEstudiante(String estudianteID, String learningPathID) {
		boolean validInput = false;
		
		while (!validInput) {
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
				String porcentajeExito = progreso.get("Porcentaje de Exito");
				System.out.println("Progreso del Estudiante: " + porcentajeExito);
				return porcentajeExito;
			} else {
				System.out.println("Learning Path o Estudiante no encontrados. Por favor, intente de nuevo.");
			}
		}
		return "No se encontro progreso";
	}

	public static String revisarFeedback(String learningPathID) {
		boolean validInput = false;
	
		while (!validInput) {
			
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
				System.out.println("Feedbacks: " + feedbacks);
				System.out.println("ProFESOR " + profesorActual.getNombre());
				if (feedbacks == null || feedbacks.isEmpty()) {
					throw new RuntimeException("No feedback found for the given Learning Path ID.");
				}
				feedbacks.forEach(System.out::println);
				return feedbacks.toString();
			} else {
		        throw new RuntimeException("No feedback found for the given Learning Path ID.");
			}
		}
		return "No se encontro feedback";
	}

	public static String calcularRating(String learningPathID) {
		boolean validInput = false;

		while (!validInput) {
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
			    return Double.toString(rating);
			} else {
				return "No se encontro rating";
			}
		}
		return "No se encontro rating";
	}

    private static void persistData() {
    	
        persistenciaUsuarios.salvarProfesor(usuariosFile, profesorActual.getUsuarioID(), profesorActual.getNombre(), profesorActual.getContraseña(), profesorActual.getEmail(), profesorActual.getTipoUsuario());
    }
    
    
}