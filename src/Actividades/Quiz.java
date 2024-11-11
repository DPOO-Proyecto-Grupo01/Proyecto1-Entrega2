package Actividades;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Quiz extends Actividad {

	private List<Pregunta> preguntas;
	private double calificacionMinima;
	private String respuestaCorrecta;
	private String respuestaUsuario;
	public boolean aprobado;



	public Quiz(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, List<String> actividadesPrevias ,List<String> actividadesSeguimiento,
			List<Pregunta> preguntas, double calificacionMinima, String respuestaCorrecta) {

		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				actividadesPrevias, actividadesSeguimiento, QUIZ);
		// TODO Auto-generated constructor stub

		this.preguntas = preguntas;
        this.respuestaCorrecta = respuestaCorrecta;
        this.aprobado = false;
        
	}
	
	public void setRespuestaUsuario(String respuesta) {
		this.respuestaUsuario = respuesta;
	}

	public Double getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(double calificacionMinima) {
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
			JSONArray opcionesArray = new JSONArray();
			opcionesArray.putAll(pregunta.opciones);
			preguntaJSON.put("opciones", opcionesArray);

			preguntasArray.put(preguntaJSON);
		}
		newObject.put("preguntas", preguntasArray);
		newObject.put("calificacionMinima", calificacionMinima);
		newObject.put("respuestaCorrecta", respuestaCorrecta);
		newObject.put("aprobado", aprobado);
		return newObject;
	}

	@Override
	public String getTipoActividad() {
		return QUIZ;
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}

	public void completarQuiz() {
		for (Pregunta pregunta : preguntas) {
			System.out.println(pregunta.pregunta);
			System.out.println(pregunta.opciones);
		}
	}



	public boolean isAprobado() {
		if (this.respuestaUsuario.equals(this.respuestaCorrecta)) {
			this.aprobado = true;
		} else {
			this.aprobado = false;
		}
		return this.aprobado;
	}


	public void setAprobado() {
		if(this.resultado >= this.calificacion) {
            this.aprobado = true;
        }
        else {
            this.aprobado = false;
        }
	}

	@Override
	public void agregarContenido(String pregunta, List<String> opciones) {
		this.preguntas.add(new Pregunta(pregunta, opciones));
	}
}

