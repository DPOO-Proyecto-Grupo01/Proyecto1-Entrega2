package Usuarios;

import java.util.List;

import Actividades.Actividades;
import Actividades.Examen;
import Actividades.RecursoEducativo;
import Actividades.Tarea;
import LearningPaths.LearningPath;

public class Profesor extends Usuario {
	public Profesor(String UsuarioID, String contraseña) {
		super(UsuarioID, contraseña, contraseña, contraseña);
		// TODO Auto-generated constructor stub
	}

	private List<LearningPath> learningPathsCreados;
	public String profesor = "Profesor";
	
	@Override
	public String getTipoUsuario() {
		return this.profesor;

	}
	
	public void revisarEstadoActividad(Actividades actividad, String estado ) {
		// Revisa el estado de una actividad
		if (actividad.getTipoActividad().equals(Tarea.TAREA) || actividad.getTipoActividad().equals(Examen.EXAMEN)) {
			Tarea tarea = (Tarea) actividad;
			if(tarea.EstadoActual(estado).equals("Enviado")) {
				tarea.EstadoActual("Exitoso");
			} else {
				tarea.EstadoActual("Fallido");
			}
			Examen examen = (Examen) actividad;
			if (examen.EstadoActual(estado).equals("Enviado")) {
				examen.EstadoActual("Exitoso");
			} else {
				examen.EstadoActual("Fallido");
			}
		} else {
			if (actividad.getTipoActividad().equals(RecursoEducativo.RECURSOEDUCATIVO)) {
				RecursoEducativo recurso = (RecursoEducativo) actividad;
				if (recurso.EstadoActual(estado).equals("Completado")) {
					recurso.EstadoActual("Exitoso");
				} else {
					recurso.EstadoActual("Fallido");
				}
			}
			
		}
		
	}
	
	public void crearActividad(Actividades actividad) {
		// Crea una actividad
		List<Actividades> actividadesCreadas = {actividad, actividad, actividad};
		learningPathsCreados.add((LearningPath) actividadesCreadas);
	}
	
	


}