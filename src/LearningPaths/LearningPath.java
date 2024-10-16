package LearningPaths;

import java.util.Date;
import java.util.List;
import java.util.Map;

import Actividades.Actividad;
import Usuarios.Estudiante;
import Usuarios.Profesor;

public class LearningPath {
	
	private String LearningPathID;
	private String titulo;
	private String descripcion;
	private String objetivos;
	private int nivelDificultad;
	private int duracionMinutos;
	private int calificacion;
	private Date fechaCreacion;
	private Date fechaModificacion;
	private List<Actividad> actividades;
	private Map<Estudiante, Progreso> progreso;
	private Map<String, List<String>> RecomendacionProfesores;
	private List<String> intereses; 
	private List<Feedback> feedback;
	private Profesor profesor;
	
	
	
//////////////////////
	
	
	public LearningPath(String LearningPathID, String titulo, String descripcion, String objetivos,
			int nivelDificultad, int duracion, Profesor profesor, List<Actividad> actividades, List<String> intereses, Map<String, List<String>> RecomendacionProfesores) {
		}
	
	public String getLearningPathID() {
		return LearningPathID;
	}


	public void setLearningPathID(String learningPathID) {
		LearningPathID = learningPathID;
	}


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	public String getObjetivos() {
		return objetivos;
	}


	public void setObjetivos(String objetivos) {
		this.objetivos = objetivos;
	}


	public int getNivelDificultad() {
		return nivelDificultad;
	}


	public void setNivelDificultad(int nivelDificultad) {
		this.nivelDificultad = nivelDificultad;
	}


	public int getDuracionMinutos() {
		return duracionMinutos;
	}


	public void setDuracionMinutos(int duracionMinutos) {
		this.duracionMinutos = duracionMinutos;
	}


	public int getCalificacion() {
		return calificacion;
	}


	public void setCalificacion(int calificacion) {
		this.calificacion = calificacion;
	}


	public Date getFechaCreacion() {
		return fechaCreacion;
	}


	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}


	public Date getFechaModificacion() {
		return fechaModificacion;
	}


	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	


	public Map<String, List<String>> getRecomendacionProfesores() {
		return RecomendacionProfesores;
	}

	public void setRecomendacionProfesores(Map<String, List<String>> recomendacionProfesores) {
		RecomendacionProfesores = recomendacionProfesores;
	}

	public List<String> getIntereses() {
		return intereses;
	}

	public void setIntereses(List<String> intereses) {
		this.intereses = intereses;
	}

	public List<Actividad> getActividades() {
		return actividades;
	}


	public void setActividades(Actividad actividad) {
		this.actividades.add(actividad);
	}


	public Map<Estudiante, Progreso> getProgreso() {
		return progreso;
	}


	public void setProgreso(Map<Estudiante, Progreso> progreso) {
		this.progreso = progreso;
	}


	public List<Feedback> getFeedback() {
		return feedback;
	}


	public void setFeedback(List<Feedback> feedback) {
		this.feedback = feedback;
	}


	public Profesor getProfesor() {
		return profesor;
	}


	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

//////////////
	
	
	public void agregarActividad(Actividad actividad) {
		
	}
	
	public boolean eliminarActividad(Actividad actividad) {
		return false;
	}
	public List<Actividad> obtenerActividades() {
		return actividades;
	}

	public void agregarProgreso(Progreso progreso) {
	}
	
	public void obtenerProgresoEstudiante(Estudiante estudiante) {
	}
	public void agregarFeedback(Feedback feedback) {
		
	}
	public List<Feedback> obtenerFeedback() {
		return feedback;
	}
	
	public double calcularRating() {
		return 0.0;
	}
	public String obtenerDetalles() {
		return "detalles";
	}
}


