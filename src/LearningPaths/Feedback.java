package LearningPaths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import Actividades.Actividad;
import Usuarios.Estudiante;
public class Feedback {
	
	private String feedbackID;
	private String comentario;
	private int calificacion;
	private String estudiante;
	
	private LearningPath learningPath;
	private String LearningPathID;

	

	public Feedback(String feedbackID, String comentario, int calificacion, String estudianteId,
		 LearningPath learningPath) {
		super();
		this.feedbackID = feedbackID;
		this.comentario = comentario;
		this.calificacion = calificacion;
		this.estudiante = estudianteId;
		
		this.learningPath = learningPath;
		this.LearningPathID = learningPath.getLearningPathID();
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
	

	public String getEstudiante() {
		return estudiante;
	}


	public LearningPath getLearningPath() {
		return learningPath;
	}

	public  Map<String, String > mostrarFeedback() {
		Map<String, String > map = new HashMap<>();
		map.put("feedbackID", feedbackID);
		map.put("ComentarioEstudiante", comentario);
		map.put("rating", Integer.toString(calificacion));
		map.put("Estudiante", estudiante);
		map.put("Learning Path", LearningPathID);
		
		return map;
	}
}


