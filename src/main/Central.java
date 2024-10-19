package main;

import java.util.List;

import Actividades.Encuesta;
import Actividades.Examen;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import LearningPaths.Feedback;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;
import Perisistencia.PersistenciaUsuarios;
import Usuarios.Authenticator;
import Usuarios.Estudiante;
import Usuarios.Profesor;

public class Central {
	
	private List<LearningPath> learningPaths;
	private List<Feedback> feedback;
	private List<Progreso> progreso;
	private static List<Estudiante> estudiantes;
	private static List<Profesor> profesores;
	private List<RecursoEducativo> recursosEducativos;
	private List<Quiz> quizes;
	private List<Tarea> tareas;
	private List<Examen> examenes;
	private List<Encuesta> encuestas;
	private Authenticator authentication;
	private static PersistenciaUsuarios persistenciaUsuarios;
	private static final String usuarios ="src\\datos\\users.json";
	
	
	public static void main(String[] args) {
		
		persistenciaUsuarios = new PersistenciaUsuarios();
		
		try {
			estudiantes=persistenciaUsuarios.cargarEstudiantes(usuarios);
			profesores=persistenciaUsuarios.cargarProfesores(usuarios);
			persistenciaUsuarios.salvarEstudiante(usuarios, "123","Juan", "Pepito", "Email", "Estudiante");
			for (Estudiante e : estudiantes) {
				System.out.println(e.getNombre());
			}
			System.out.println("Nuevos");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
