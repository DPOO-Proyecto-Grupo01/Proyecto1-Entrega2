package Actividades;

import java.util.Date;
import java.util.List;

public class Encuesta extends Actividad {
	
	private List<String> preguntas;

	public Encuesta(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, 
			List<Actividad> actividadesPrevia, List<String> actividadesSeguimiento, List<String> preguntas) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado, actividadesPrevia, actividadesSeguimiento, ENCUESTA);
		// TODO Auto-generated constructor stub
		
		this.preguntas = preguntas;
		
	}
	
	
	public List<String> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
	}


	public static final String ENCUESTA = "Encuesta";
	
	@Override
	public String getTipoActividad() {
		return ENCUESTA;
	}

	@Override
	public void agregarContenido(String pregunta) {
		// TODO Auto-generated method stub
		
	}
	
	public void completarEncuesta() {
		// Responder la encuesta
		System.out.println(this.preguntas);
	}

	
	

}
