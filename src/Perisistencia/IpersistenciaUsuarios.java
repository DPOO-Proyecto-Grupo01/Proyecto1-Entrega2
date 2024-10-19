package Perisistencia;

import java.io.IOException;
import java.util.List;

import Usuarios.Estudiante;
import Usuarios.Profesor;
import Usuarios.Usuario;

public interface IpersistenciaUsuarios {
	
	public List<Estudiante> cargarEstudiantes(String archivo) throws Exception;
	
	public List<Profesor> cargarProfesores(String archivo) throws Exception;

	public void salvarEstudiante(String archivo, String UsuarioID, String nombre, String contraseña, String email, String tipoUsuario);
		
	
	public void salvarProfesor(String archivo, String UsuarioID, String nombre, String contraseña, String email, String tipoUsuario);

}
