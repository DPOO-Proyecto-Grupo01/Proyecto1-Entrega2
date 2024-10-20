package Actividades;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Quiz extends Actividad {

	private List<Pregunta> preguntas;
	private double calificacionMinima;
	private String respuestaCorrecta;
	public boolean aprobado;



	public Quiz(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, List<String> actividadesPrevias ,List<String> actividadesSeguimiento,
			List<Pregunta> preguntas, double calificacionMinima, String respuestaCorrecta) {

		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado,actividadesPrevias, actividadesSeguimiento, QUIZ);
		// TODO Auto-generated constructor stub

		this.preguntas = preguntas;
		this.calificacionMinima = calificacionMinima;
        this.respuestaCorrecta = respuestaCorrecta;
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

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
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

	@Override
	public void agregarContenido(String pregunta, List<String> opciones) {
		this.preguntas.add(new Pregunta(pregunta, opciones));
	}
}

