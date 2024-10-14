package Actividades;

public abstract class Actividades {

	private String actividadID;
    private String descripcion;
    private String objetivo;
    private int nivelDificultad;
    private int duracionEsperada;
    private boolean esObligatoria;
    private int fechaLimite;
    private String resenas;
    private double calificacion;
    private int resultado;
    protected String preguntas;
    
	public Actividades(String actividadID, String descripcion, String objetivo, int nivelDificultad,
			int duracionEsperada, boolean esObligatoria, int fechaLimite, String resenas, double calificacion,
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



	public int getDuracionEsperada() {
		return duracionEsperada;
	}



	public void setDuracionEsperada(int duracionEsperada) {
		this.duracionEsperada = duracionEsperada;
	}



	public int getFechaLimite() {
		return fechaLimite;
	}



	public void setFechaLimite(int fechaLimite) {
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



	public abstract void agregarContenido(String pregunta);
	
	public abstract void ObtenerResultado();
	
	public abstract void validarRespuesta();
	
	public abstract void calificarActividad();
	
	public abstract void iniciarActividad();
	

	
	
	
	
}
