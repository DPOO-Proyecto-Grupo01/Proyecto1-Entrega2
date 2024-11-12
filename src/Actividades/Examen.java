package Actividades;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Examen extends Actividad{
	
	public List<Pregunta> preguntas;
	public double calificacionMinima;
	

	public Examen(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, double resultado, List<String> actividadesPrevias,
			 List<String> actividadesSeguimiento, List<Pregunta> preguntas, double calificacionMinima) {

		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				 actividadesPrevias,actividadesSeguimiento, EXAMEN);
		this.preguntas = preguntas;
		this.calificacionMinima = calificacionMinima;
		
	}


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

		//Valores especificos de la clase
		JSONArray preguntasArray = new JSONArray();
		for (Pregunta pregunta : preguntas) {
			JSONObject preguntaJSON = new JSONObject();
			preguntaJSON.put("pregunta", pregunta.pregunta);

			preguntasArray.put(preguntaJSON);
		}
		newObject.put("preguntas", preguntasArray);
		newObject.put("calificacionMinima", calificacionMinima);
		return newObject;

	}

	@Override
	public String getTipoActividad() {
		return EXAMEN;
	
	}

	@Override
	public void agregarContenido(String pregunta, List<String> opciones) {
		this.preguntas.add(new Pregunta(pregunta, opciones));
	}


	public String EstadoActual(String estado) {
		// TODO Auto-generated method stub
		return null;
	}

	public Double getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(double calificacionMinima) {
		this.calificacionMinima = calificacionMinima;
	}



	public List<Pregunta> getPreguntas() {
		return preguntas;
	}



	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public void completarExamen() {
		System.out.println(this.preguntas);
	}



	
	
	

}
