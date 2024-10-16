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
	private List<String> intereses; 
	private List<Feedback> feedback;
	private Profesor profesor;
	public Progreso progreso;
	
	
	
//////////////////////
	
	
	public LearningPath(String LearningPathID, String titulo, String descripcion, String objetivos,
			int nivelDificultad, int duracion, Profesor profesor, List<Actividad> actividades, List<String> intereses) {
		this.LearningPathID = LearningPathID;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.objetivos = objetivos;
		this.nivelDificultad = nivelDificultad;
		this.duracionMinutos = duracion;
		this.profesor = profesor;
		this.actividades = actividades;
		this.intereses = intereses;
		
		
		}
	
	
	
	public Progreso getProgreso() {
		return progreso;
	}



	public void setProgreso(Progreso progreso) {
		this.progreso = progreso;
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
	
	
	public List<Actividad> obtenerActividades() {
		return actividades;
	}

	public void actualizarProgreso(Progreso progreso, List<Actividad> actividades) {
		int exitosas = 0;
		double porcentajeExitosas = 0;
		for (Actividad actividad : actividades) {
			if (actividad.getEstado().equals("Exitoso")) {
				exitosas++;
			}
		}
		porcentajeExitosas = exitosas/actividades.size();
		progreso.setPorcentajeDeExito(porcentajeExitosas);
	
		
	}
	
	public void obtenerProgresoEstudiante(Estudiante estudiante) {
	}

	public List<Feedback> obtenerFeedback() {
		return feedback;
	}
	
	public double calcularRating() {
		double rating = 0;
		for (Feedback f : feedback) {
			rating += f.getCalificacion();
		}
		return rating/feedback.size();
	}
	public String obtenerDetalles() {
		return "Titulo: " + titulo + "\n" + "Descripcion: " + descripcion + "\n" + "Objetivos: " + objetivos + "\n" + "Nivel de Dificultad: " + nivelDificultad + "\n" + "Duracion: " + duracionMinutos + "\n" 
	+ "Calificacion: " + calificacion + "\n" + "Fecha de Creacion: " + fechaCreacion + "\n" + "Fecha de Modificacion: " + fechaModificacion + "\n" + "Intereses: " + intereses + "\n" + "Profesor: " + profesor;
		
	}
}


