package Perisistencia;

public interface IpersistenciaActividades {
	
	public void cargarActividad(String archivo, String actividadID);
	
	public void salvarActividad(String archivo, String actividadID);

}
