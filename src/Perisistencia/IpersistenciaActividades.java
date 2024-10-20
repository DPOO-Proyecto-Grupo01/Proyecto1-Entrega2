package Perisistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import Actividades.Actividad;
import Actividades.Encuesta;
import Actividades.Examen;
import Actividades.Quiz;
import Actividades.RecursoEducativo;
import Actividades.Tarea;

public interface IpersistenciaActividades {
	
	public ArrayList<Actividad> cargarActividades(String archivo)throws Exception;

	
	public void salvarActividad(String archivo, Actividad actividad);

}
