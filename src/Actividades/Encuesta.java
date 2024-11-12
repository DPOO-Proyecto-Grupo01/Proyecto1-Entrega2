package Actividades;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Encuesta extends Actividad {
	
	public List<String> preguntasAbiertas;
	private String tipo;

	public Encuesta(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, double resultado, List<String> actividadesPrevias,
			List<String> actividadesSeguimiento, List<String> preguntas) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				actividadesPrevias, actividadesSeguimiento, ENCUESTA);
		// TODO Auto-generated constructor stub
		
		this.tipo=ENCUESTA;
		this.preguntasAbiertas = preguntas;
		
	}
	
	
	public List<String> getPreguntas() {
		return preguntasAbiertas;
	}

	public void setPreguntas(List<String> preguntas) {
		this.preguntasAbiertas = preguntas;
	}


	public static final String ENCUESTA = "Encuesta";

	@Override
	public JSONObject convertToJSONObject() {
		// Valores genericos de actividad
				JSONObject newObject = new JSONObject();
				newObject.put("actividadID", actividadID);
				newObject.put("descripcion", descripcion);
				newObject.put("objetivo", objetivo);
				newObject.put("nivelDificultad", nivelDificultad);
				newObject.put("duracionEsperada", duracionEsperada);
				newObject.put("esObligatoria", esObligatoria);
				newObject.put("fechaLimite", fechaLimite.getTime());
				newObject.put("resenas", resenas);
				newObject.put("calificacion", calificacion);
				newObject.put("resultado", resultado);
				newObject.put("actividadesPrevias", actividadesPrevias);
				newObject.put("actividadesSeguimiento", actividadesSeguimiento);
				newObject.put("tipoActividad", tipoActividad);

				
				newObject.put("preguntasAbiertas", preguntasAbiertas);
				return newObject;
	}

	@Override
	public String getTipoActividad() {
		return ENCUESTA;
	}

	@Override
	public void agregarContenido(String pregunta, List<String> opciones) {
		
	}

	
	public void completarEncuesta() {
		// Responder la encuesta
		System.out.println(this.preguntasAbiertas);
	}

	
	

}
