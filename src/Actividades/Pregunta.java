package Actividades;

import java.util.List;

/**
 * Clase simple para organizar estructuras de las preguntas para el Quiz
 */
public class Pregunta{
	String pregunta;
	List<String> opciones;
	public Pregunta(String pregunta, List<String> opciones) {
		this.pregunta = pregunta;
		this.opciones = opciones;
	}
}
