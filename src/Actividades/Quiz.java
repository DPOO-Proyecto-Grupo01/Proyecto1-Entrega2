package Actividades;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Quiz extends Actividad {
	
	public static final String QUIZ = "Quiz";
	private double calificacionMinima;
	private Map<String, List<String>> preguntas;
	public boolean aprobado;
	private String respuestaCorrecta;
	private String tipo;
	


	public Quiz(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, List<String> actividadesPrevias ,List<String> actividadesSeguimiento, 
			Map<String, List<String>> preguntas, double calificacionMinima, String tipo) {

		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado,actividadesPrevias, actividadesSeguimiento, QUIZ);
		// TODO Auto-generated constructor stub
		
		this.tipo=QUIZ;
		this.preguntas = preguntas;
		this.calificacionMinima = calificacionMinima;
		this.aprobado = false;
	}
	
	
	
	

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
	public void agregarContenido(String pregunta, List<String> opciones) {
		// TODO Auto-generated method stub
		
	}


	public Map<String, List<String>> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(Map<String, List<String>> preguntas) {
		this.preguntas = preguntas;
	}

	public void completarQuiz() {
		System.out.println(this.preguntas);
	}



	public boolean isAprobado() {
		return aprobado;
	}


	public void setAprobado() {
		if(this.resultado >= this.calificacion) {
            this.aprobado = true;
        }
        else {
            this.aprobado = false;
        }
	}
	
	

}
