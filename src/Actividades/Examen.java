package Actividades;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Examen extends Actividad{
	
	public static final String EXAMEN = "Examen";
	public Map<String, List<String>> preguntas;
	public double calificacionMinima;
	public String tipo;
	

	public Examen(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, List<String> actividadesPrevias,
			 List<String> actividadesSeguimiento, Map<String, List<String>> preguntas, double calificacionMinima, String tipo) {

		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado, actividadesPrevias,actividadesSeguimiento, EXAMEN);
		// TODO Auto-generated constructor stub
		
		this.tipo=EXAMEN;
		this.preguntas = preguntas;
		this.calificacionMinima = calificacionMinima;
		
	}

	

	@Override
	public String getTipoActividad() {
		return EXAMEN;
	
	}

	@Override
	public void agregarContenido(String pregunta, List<String> opciones) {
		this.preguntas.put(pregunta, opciones);
		

	
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



	public Map<String, List<String>> getPreguntas() {
		return preguntas;
	}



	public void setPreguntas(Map<String, List<String>> preguntas) {
		this.preguntas = preguntas;
	}

	public void completarExamen() {
		System.out.println(this.preguntas);
	}
	
	
	
	

}
