package Actividades;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Tarea extends Actividad {
	
	public static final String TAREA = "Tarea";
	private String estado;
	private String Instrucciones;
	private Map<String, List<String>> preguntas;
	private Collection<List<String>> opciones;
	private String tipo;
	
	public Tarea(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado,List<String> actividadesPrevias,  
			List<String> actividadesSeguimiento, Map<String, List<String>>preguntas, String Instrucciones, String tipo, String estado ) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado,actividadesPrevias, actividadesSeguimiento, TAREA);
		
		this.tipo=TAREA;
		this.estado = estado;
		this.Instrucciones = Instrucciones;
		this.preguntas = preguntas;
		

	}
	
	public void getOpciones(Map<String, List<String>> preguntas) {
		this.opciones = preguntas.values();
	
		
	}
	
	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getObjetivo() {
		return objetivo;
	}



	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}



	public int getDuracionEsperada() {
		return duracionEsperada;
	}



	public void setDuracionEsperada(int duracionEsperada) {
		this.duracionEsperada = duracionEsperada;
	}



	public Date getFechaLimite() {
		return fechaLimite;
	}



	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}



	public String getActividadID() {
		return actividadID;
	}



	public int getNivelDificultad() {
		return nivelDificultad;
	}



	public boolean isEsObligatoria() {
		return esObligatoria;
	}



	public String getResenas() {
		return resenas;
	}



	public double getCalificacion() {
		return calificacion;
	}



	public int getResultado() {
		return resultado;
	}
	

	@Override
	public String getTipoActividad() {
		return TAREA;
	}

	@Override
	public void agregarContenido(String pregunta, List<String> opciones) {
		
		this.preguntas.put(pregunta, opciones);		
	}

	public String getInstrucciones() {
		return Instrucciones;
	}
	
	public void setInstrucciones(String Instrucciones) {
		this.Instrucciones = Instrucciones;
	}
	
	public String registrarEntrega(String medioEntrega) {
		return medioEntrega; 
		
	}

	public Map<String, List<String>> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(Map<String, List<String>> preguntas) {
		this.preguntas = preguntas;
	}
	
	
	public void completarTarea() {
		System.out.println(this.preguntas);
	}

	
	
	

}
