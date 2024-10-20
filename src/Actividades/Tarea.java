package Actividades;

import java.util.Date;
import java.util.List;

public class Tarea extends Actividad {
	
	public static final String TAREA = "Tarea";
	private String estado;
	private String Instrucciones;
	private List<String> preguntas;
	
	public Tarea(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, 

			List<Actividad> actividadesPrevia, List<String> actividadesSeguimiento, String estado, String Instrucciones, List<String> preguntas) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado, actividadesPrevia, actividadesSeguimiento, TAREA);
		this.estado = estado;
		this.Instrucciones = Instrucciones;
		this.preguntas = preguntas;
		

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
	public void agregarContenido(String pregunta) {
		this.preguntas.add(pregunta);
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

	public List<String> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<String> preguntas) {
		this.preguntas = preguntas;
	}
	
	
	public void completarTarea() {
		System.out.println(this.preguntas);
	}
	
	

}
