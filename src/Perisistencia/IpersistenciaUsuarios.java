package Perisistencia;

public interface IpersistenciaUsuarios {
	
	public void cargarUsuario(String archivo, String usuarioID);

	public void salvarUsuario(String archivo, String usuarioID);

}
