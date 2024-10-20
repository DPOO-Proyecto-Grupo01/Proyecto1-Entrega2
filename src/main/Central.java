package main;

import java.util.ArrayList;
import java.util.List;

import Actividades.Encuesta;
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
	
	private static List<LearningPath> learningPaths;
	private List<Feedback> feedback;
	private List<Progreso> progreso;
	private static List<Estudiante> estudiantes;
	private static List<Profesor> profesores;
	private List<RecursoEducativo> recursosEducativos;
	private static List<Quiz> quizes;
	private List<Tarea> tareas;
	private List<Examen> examenes;
	private List<Encuesta> encuestas;
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
			estudiantes=persistenciaUsuarios.cargarEstudiantes(usuarios);
			profesores=persistenciaUsuarios.cargarProfesores(usuarios);
			quizes=(List<Quiz>) persistenciaActividades.cargarActividad(actividadesFile).get(0);
			learningPaths=persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
			ArrayList<String> actividadesID = new ArrayList<String>();
			actividadesID.add("A505");
			actividadesID.add("A304");
			ArrayList<String> intereses = new ArrayList<String>();
			intereses.add("Java");
			intereses.add("Programacion");
			learningPaths.add(new LearningPath("LP105", "Aprendiendo a programar en Java", "Descripcion", "Objetivos", 3, 120, "P105", actividadesID, intereses));
			
			for (Estudiante e : estudiantes) {
				System.out.println(e.getNombre());
			}
			for (LearningPath l : learningPaths) {
				System.out.println(l.getTitulo());
			}
			for (Quiz q : quizes) {
				System.out.println(q.getDescripcion());
			}
			System.out.println("Nuevos");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}