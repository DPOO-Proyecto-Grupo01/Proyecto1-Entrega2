package LearningPaths;
import java.util.Date;

import Actividades.Actividades;
import Usuarios.Estudiante;
public class Feedback {
	
	private String feedbackID;
	private String comentario;
	private double calificacion;
	private Date fecha;
	private Estudiante estudiante;
	private Actividades actividad;
	private LearningPath learningPath;

	

	public Feedback(String feedbackID, String comentario, double calificacion, Date fecha, Estudiante estudiante,
			Actividades actividad, LearningPath learningPath) {
		super();
		this.feedbackID = feedbackID;
		this.comentario = comentario;
		this.calificacion = calificacion;
		this.fecha = fecha;
		this.estudiante = estudiante;
		this.actividad = actividad;
		this.learningPath = learningPath;
	}

	public String getFeedbackID() {
		return feedbackID;
	}

	public void setFeedbackID(String feedbackID) {
		this.feedbackID = feedbackID;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public double getCalificacion() {
		return calificacion;
	}

	

	public Date getFecha() {
		return fecha;
	}

	

	public Estudiante getEstudiante() {
		return estudiante;
	}

	

	public Actividades getActividad() {
		return actividad;
	}

	

	public LearningPath getLearningPath() {
		return learningPath;
	}

	
}


