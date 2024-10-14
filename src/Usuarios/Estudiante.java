package Usuarios;

import java.util.List;
import java.util.Map;

import Actividades.Actividades;
import LearningPaths.LearningPath;

public class Estudiante extends Usuario {
	
	public Estudiante(String UsuarioID, String contraseña) {
		super(UsuarioID, contraseña);
		// TODO Auto-generated constructor stub
	}

	private List<LearningPath> learningPathsInscritos;
	private Map<Actividades, String> progresoActividades;
	public String estudiante = "Estudiante";

	@Override
	public String getTipoUsuario() {
		return this.estudiante;
	}
}