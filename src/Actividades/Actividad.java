package Actividades;

import org.json.JSONObject;

import java.io.IOException;
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
	protected String estado;
	protected List<String> actividadesPrevias;
	protected List<String> actividadesSeguimiento;
	protected String tipoActividad;
	private Date fechainicio;
	private Date fechafin;
	private String actividadPrevia;
	public String learningPathAlQuePertenece;


	public String getLearningPathAlQuePertenece() {
		return learningPathAlQuePertenece;
	}

	public void setLearningPathAlQuePertenece(String learningPathAlQuePertenece) {
		this.learningPathAlQuePertenece = learningPathAlQuePertenece;
	}

	//TODO: MOVER TODOS LOS STRINGS A UNA CLASE DE CONSTANTES
    public static final String TAREA = "Tarea";
    public static final String EXAMEN = "Examen";
    public static final String RECURSOEDUCATIVO = "Recurso Educativo";
    public static final String QUIZ = "Quiz";
    public static final String ENCUESTA = "Encuesta";
    
   
    
	public Actividad(String actividadID, String descripcion, String objetivo, int nivelDificultad,
			int duracionEsperada, boolean esObligatoria, Date fechaLimite2, String resenas,
			List<String> actividadesPrevias, List<String> actividadesSeguimiento, String tipoActividad) {
		this.actividadID = actividadID;
		this.descripcion = descripcion;
		this.objetivo = objetivo;
		this.nivelDificultad = nivelDificultad;
		this.duracionEsperada = duracionEsperada;
		this.esObligatoria = esObligatoria;
		this.fechaLimite = fechaLimite2;
		this.resenas = resenas;
		this.calificacion = calificacion;
		this.resultado = resultado;
		this.setActividadesPrevias(actividadesPrevias);
		this.setActividadesSeguimiento(actividadesSeguimiento);
        this.setTipoActividad(tipoActividad);
    }

	public abstract JSONObject convertToJSONObject();
	

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




	public void setFechaLimite(Actividad actividad) {
	    Date inicio = actividad.getFechainicio();
	    if (inicio != null) {
	        long inicioMillis = inicio.getTime();
	        long duracionMillis = this.duracionEsperada +2L*60L*60L*1000L;
	        Date finall = new Date(inicioMillis + duracionMillis);
	        this.fechaLimite = finall;
	    } else {
	        this.fechaLimite = null;
	    }
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
	
    
    public String getActividadPrevia() {
		return actividadPrevia;
	}

	public void setActividadPrevia(String actividadPrevia) {
		this.actividadPrevia = actividadPrevia;
	}


	public String getTipoActividad(){
		return tipoActividad;
	}
	
	public Date getFechainicio() {
		return fechainicio;
	}
	
	public Date getFechafin() {
		return fechafin;
	}	
	
	public void setFechainicio(Date fechain) {
		this.fechainicio = fechain;
	}
	
	public void setFechafin(Date fechafin) {
		this.fechafin = fechafin;
	}
	
	
	public abstract void agregarContenido(String pregunta, List<String> opciones);


	public List<String> getActividadesPrevias() {
		return actividadesPrevias;
	}

	public void setActividadesPrevias(List<String> actividadesPrevias) {
		this.actividadesPrevias = actividadesPrevias;
	}

	public List<String> getActividadesSeguimiento() {
		return actividadesSeguimiento;
	}

	public void setActividadesSeguimiento(List<String> actividadesSeguimiento) {
		this.actividadesSeguimiento = actividadesSeguimiento;
	}

	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public Double getCalificacionMinima() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public JSONObject toJSON() {
		// TODO Ver si falta algun atributo, yo segui lo que vi en learning_paths.json
		JSONObject objeto = new JSONObject();
		objeto.put("descripcion", descripcion);
		objeto.put("calificacion", calificacion);
		objeto.put("nivelDificultad", nivelDificultad);
		objeto.put("resultado", resultado);
		objeto.put("PreguntasAbiertas", preguntas);
		objeto.put("tipoActividad", tipoActividad);
		objeto.put("objetivo", objetivo);
		objeto.put("actividadesPrevias", actividadesPrevias);
		objeto.put("actividadesSeguimiento", actividadesSeguimiento);
		objeto.put("fechaLimite", fechaLimite);
		objeto.put("duracionEsperada", duracionEsperada);
		objeto.put("resenas", actividadesSeguimiento);
		objeto.put("actividadID", actividadID);
		objeto.put("esObligatoria", esObligatoria);
		return objeto;
	}
	
	
}
