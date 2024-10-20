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
	private static List<RecursoEducativo> recursosEducativos;
	private static List<Quiz> quizes;
	private static List<Tarea> tareas;
	private static List<Examen> examenes;
	private static List<Encuesta> encuestas;
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
			quizes=persistenciaActividades.cargarQuizes(actividadesFile);
			examenes=persistenciaActividades.cargarExamenes(actividadesFile);
			encuestas=persistenciaActividades.cargarEncuestas(actividadesFile);
			tareas=persistenciaActividades.cargarTareas(actividadesFile);
			recursosEducativos=persistenciaActividades.cargarRecursosEducativos(actividadesFile);
			
			learningPaths=persistenciaLearningPaths.cargarLearningPath(learningPathsFile);
			ArrayList<String> actividadesID = new ArrayList<String>();
			actividadesID.add("A505");
			actividadesID.add("A304");
			ArrayList<String> intereses = new ArrayList<String>();
			intereses.add("Java");
			intereses.add("Programacion");
			learningPaths.add(new LearningPath("LP105", "Aprendiendo a programar en Java", "Descripcion", "Objetivos", 3, 120, "P105", actividadesID, intereses));
			
			for (Quiz q : quizes) {
				System.out.println(q.getTipoActividad());
			}
			for (Encuesta e : encuestas) {
				System.out.println(e.getTipoActividad());
			}
			for (Tarea t : tareas) {
				System.out.println(t.getTipoActividad());
			}
			for (Examen e : examenes) {
				System.out.println(e.getTipoActividad());
			}
			for (RecursoEducativo r : recursosEducativos) {
				System.out.println(r.getTipoActividad());
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
