package Perisistencia;

import java.io.IOException;

import Usuarios.Usuario;

public interface IpersistenciaUsuarios {
	
	public void cargarUsuario(String archivo, Usuario usuarioID) throws Exception;

	public void salvarUsuario(String archivo, Usuario usuarioID);

}
