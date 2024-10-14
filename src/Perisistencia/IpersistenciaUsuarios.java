package Perisistencia;

import java.io.IOException;

public interface IpersistenciaUsuarios {
	
	public void cargarUsuario(String archivo, String usuarioID) throws Exception;

	public void salvarUsuario(String archivo, String usuarioID);

}
