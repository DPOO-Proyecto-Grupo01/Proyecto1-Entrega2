package Actividades;

import java.util.Date;
import java.util.List;

public class Examen extends Actividad{
	
	public static final String EXAMEN = "Examen";
	public List<String> preguntas;
	public double calificacionMinima;
	

	public Examen(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, 
			List<Actividad> actividadesPrevia, List<String> actividadesSeguimiento, List<String> preguntas, double calificacionMinima) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado, actividadesPrevia, actividadesSeguimiento, EXAMEN);
		// TODO Auto-generated constructor stub
		
		this.preguntas = preguntas;
		this.calificacionMinima = calificacionMinima;
		
	}

	

	@Override
	public String getTipoActividad() {
		return EXAMEN;
	
	}

	@Override
	public void agregarContenido(String pregunta) {
		preguntas.add(pregunta);

	
	}

	

	public String EstadoActual(String estado) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public double getCalificacionMinima() {
		return calificacionMinima;
	}
	
	public void setCalificacionMinima(double calificacionMinima) {
		this.calificacionMinima = calificacionMinima;
	}



	public List<String> getPreguntas() {
		return preguntas;
	}



	public void setPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
	}

	public void completarExamen() {
		System.out.println(this.preguntas);
	}
	
	
	
	

}
