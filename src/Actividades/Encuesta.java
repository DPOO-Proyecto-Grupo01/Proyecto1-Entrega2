package Actividades;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Encuesta extends Actividad {
	
	public List<String> preguntas;
	private String tipo;

	public Encuesta(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, List<String> actividadesPrevias,
			List<String> actividadesSeguimiento, List<String> preguntas) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado, actividadesPrevias, actividadesSeguimiento, ENCUESTA);
		// TODO Auto-generated constructor stub
		
		this.tipo=ENCUESTA;
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
	public JSONObject convertToJSONObject() {
		return null;
	}

	@Override
	public String getTipoActividad() {
		return ENCUESTA;
	}

	@Override
	public void agregarContenido(String pregunta, List<String> opciones) {
		this.preguntas.add(pregunta);
		
	}
	
	public void completarEncuesta() {
		// Responder la encuesta
		System.out.println(this.preguntas);
	}

	
	

}
