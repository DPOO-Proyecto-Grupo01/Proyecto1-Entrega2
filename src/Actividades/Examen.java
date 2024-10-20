package Actividades;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Examen extends Actividad{
	
	public List<Pregunta> preguntas;
	public double calificacionMinima;
	public String tipo;
	

	public Examen(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, List<String> actividadesPrevias,
			 List<String> actividadesSeguimiento, List<Pregunta> preguntas, double calificacionMinima) {

		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				 actividadesPrevias,actividadesSeguimiento, EXAMEN);
		this.tipo=EXAMEN;
		this.preguntas = preguntas;
		this.calificacionMinima = calificacionMinima;
		
	}


	@Override
	public JSONObject convertToJSONObject() {
		return null;
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

	public double getCalificacionMinima() {
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
