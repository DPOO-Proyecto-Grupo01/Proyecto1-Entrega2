package Usuarios;

import java.util.List;

import LearningPaths.LearningPath;

public class Profesor extends Usuario {
	public Profesor(String UsuarioID, String contraseña) {
		super(UsuarioID, contraseña);
		// TODO Auto-generated constructor stub
	}

	private List<LearningPath> learningPathsCreados;
	public String profesor = "Profesor";
	
	@Override
	public String getTipoUsuario() {
		return this.profesor;

}}