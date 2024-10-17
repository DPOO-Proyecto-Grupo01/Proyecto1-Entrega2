package Actividades;

import java.util.Date;
import java.util.List;

public abstract class Actividad {

	protected String actividadID;
    protected String descripcion;
    protected String objetivo;
    protected int nivelDificultad;
    protected int duracionEsperada;
    protected boolean esObligatoria;
    protected Date fechaLimite;
    protected String resenas;
    protected double calificacion;
    protected int resultado;
    protected String preguntas;
    protected List<String> respuestasUsuario;
    public String estado;
   
    
    
    
    //TODO: MOVER TODOS LOS STRINGS A UNA CLASE DE CONSTANTES
    public static final String TAREA = "Tarea";
    public static final String EXAMEN = "Examen";
    public static final String RECURSOEDUCATIVO = "Recurso Educativo";
    public static final String QUIZ = "Quiz";
    public static final String ENCUESTA = "Encuesta";
    
   
    
	public Actividad(String actividadID, String descripcion, String objetivo, int nivelDificultad,
			int duracionEsperada, boolean esObligatoria, Date fechaLimite, String resenas, double calificacion,
			int resultado) {
		this.actividadID = actividadID;
		this.descripcion = descripcion;
		this.objetivo = objetivo;
		this.nivelDificultad = nivelDificultad;
		this.duracionEsperada = duracionEsperada;
		this.esObligatoria = esObligatoria;
		this.fechaLimite = fechaLimite;
		this.resenas = resenas;
		this.calificacion = calificacion;
		this.resultado = resultado;
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


	public static String getTarea() {
		return TAREA;
	}



	public static String getExamen() {
		return EXAMEN;
	}



	public static String getRecursoeducativo() {
		return RECURSOEDUCATIVO;
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
	
	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public abstract String getTipoActividad();
	
	public abstract void agregarContenido(String pregunta);


		
	
	
	

	
	
	
	
}
