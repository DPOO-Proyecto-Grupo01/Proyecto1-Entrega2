package Actividades;

import java.util.Date;
import java.util.List;

public class Quiz extends Actividad {
	
	public static final String QUIZ = "Quiz";
	private double calificacionMinima;
	private List<String> preguntas;
	public boolean aprobado;
	private String respuestaCorrecta;
	


	public Quiz(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, 
			List<Actividad> actividadesPrevia, Actividad actividadesSeguimiento) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado, actividadesPrevia, actividadesSeguimiento);
		// TODO Auto-generated constructor stub
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
	public void agregarContenido(String pregunta) {
		// TODO Auto-generated method stub
		
	}





	public List<String> getPreguntas() {
		return preguntas;
	}





	public void setPreguntas(List<String> preguntas) {
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
