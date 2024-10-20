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
		persistenciaActividades = new PersistenciaActividades();
		persistenciaUsuarios = new PersistenciaUsuarios();
		persistenciaLearningPaths = new PersistenciaLearningPaths();
		
		try {
			actividades = new ArrayList<Actividad>();
			
			estudiantes=persistenciaUsuarios.cargarEstudiantes(usuarios);
			profesores=persistenciaUsuarios.cargarProfesores(usuarios);

			learningPaths=persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
			ArrayList<String> actividadesID = new ArrayList<String>();
			actividadesID.add("A505");
			actividadesID.add("A304");
			ArrayList<String> intereses = new ArrayList<String>();
			intereses.add("Java");
			intereses.add("Programacion");
			learningPaths.add(new LearningPath("LP105", "Aprendiendo a programar en Java", "Descripcion", "Objetivos", 3, 120, "P105", actividadesID, intereses));
			
			for (Actividad a: actividades) {
				System.out.println("ID: "+ a.getActividadID() + ". Tipo: "+ a.getTipoActividad());
			}
			
			for (Profesor p : profesores) {
				System.out.println(p.getNombre());
			}
			for (LearningPath l : learningPaths) {
				System.out.println(l.getTitulo());
			}
			
			System.out.println("Nuevos");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}



}
