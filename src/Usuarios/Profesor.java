package Usuarios;

import java.util.List;

import Actividades.Actividad;

import Actividades.Examen;
import Actividades.Quiz;
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
	
	public void revisarEstadoActividad(Actividad actividad, String estado ) {
		// Revisa el estado de una actividad
		if (actividad.getTipoActividad().equals("Tarea") || actividad.getTipoActividad().equals("Examen")) {
			if(actividad.EstadoActual(estado).equals("Enviado")) {
				actividad.EstadoActual("Exitoso");
			} else {
				actividad.EstadoActual("Fallido");
			}
		} else if (actividad.getTipoActividad().equals("RecursoEducativo") || actividad.getTipoActividad().equals("Encuesta")) {
            actividad.EstadoActual("Exitoso");
        } else if (actividad.getTipoActividad().equals("Quiz")){
			if (actividad.getCalificacion() >= ((Quiz) actividad).getCalificacionMinima()) {
				actividad.EstadoActual("Exitoso");
			} else {
				actividad.EstadoActual("Fallido");
			}
        	
			
		} 
	}
		
			
		
		
	
	
	public void crearActividad(Actividad actividad) {
		// Crea una actividad
		
		newInstanceOfActividad(actividad);
		List<Actividades> actividadesCreadas = {actividad, actividad, actividad};
		learningPathsCreados.add((LearningPath) actividadesCreadas);
	}
	
	public void CalificacionMinima(Actividad actividad, double calificacionMinima) {
		if(actividad.getTipoActividad().equals("Quiz")) {
			((Quiz) actividad).setCalificacionMinima(calificacionMinima);
		} else {
			if (actividad.getTipoActividad().equals("Examen")) {
				((Examen) actividad).setCalificacionMinima(calificacionMinima);
			}
		}
		
		
	}
	
	


}