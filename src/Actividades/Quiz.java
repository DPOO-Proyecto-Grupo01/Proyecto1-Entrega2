package Actividades;

import java.util.Date;
import java.util.List;

public class Quiz extends Actividad {

	public Quiz(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, 
			List<String> actividadesPrevia, List<String> actividadesSeguimiento) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado, actividadesPrevia, actividadesSeguimiento);
		// TODO Auto-generated constructor stub
	}
	
	public static final String QUIZ = "Quiz";
	private double calificacionMinima;
	
	

	public double getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(double calificacionMinima) {
		this.calificacionMinima = calificacionMinima;
	}

	@Override
	public String getTipoActividad() {
		return QUIZ;
	}

	@Override
	public void agregarContenido(String pregunta) {
		// TODO Auto-generated method stub
		
	}

	
	

}
