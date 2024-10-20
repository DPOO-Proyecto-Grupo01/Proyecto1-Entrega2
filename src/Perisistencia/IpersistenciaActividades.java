package Perisistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import Actividades.Actividad;

public interface IpersistenciaActividades {
	
	public ArrayList<Actividad> cargarActividad(String archivo)throws Exception;
	
	public void salvarActividad(String archivo, String actividadID, String descripcion, String objetivo, int nivelDificultad,
			int duracionEsperada, boolean esObligatoria, Date fechaLimite2, String resenas, double calificacion,
			int resultado, List<String> actividadesPrevia, List<String> actividadesSeguimiento);

}
