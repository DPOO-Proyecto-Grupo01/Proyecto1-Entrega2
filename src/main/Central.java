package main;

import java.util.ArrayList;
import java.util.List;

import Actividades.Encuesta;
import Actividades.Actividad;
import Actividades.Examen;
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
			actividadesID.add("A505");
			actividadesID.add("A304");
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
			
			Estudiante usuario = new Estudiante( "U105", "Juan Perez", "1234", "J.perez@uniandes.edu.co", "Estudiante");
			Profesor profesor = new Profesor("P105", "Carlos Perez", "1234", "C.perez@uniandes.edu.co", "Profesor");
			estudiantes.add(usuario);
			profesores.add(profesor);
			
			if( usuario.iniciarSesion("U105", "1234") ) {
                System.out.println("Inicio de sesion exitoso: Estudiante "+ usuario.getUsuarioID());
            }
            else {
                System.out.println("Inicio de sesion fallido: Estudiante");
            }
			
			if(profesor.iniciarSesion("P105", "1234")) {
                System.out.println("Inicio de sesion exitoso: Profesor "+ profesor.getUsuarioID());
            }
            else {
                System.out.println("Inicio de sesion fallido: Profesor");
            }
			
			
			
			
			
			
			
			
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
	



}
