package LearningPaths;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import Actividades.Actividad;
import Usuarios.Estudiante;
public class Feedback {
	
	private String feedbackID;
	private String comentario;
	private int calificacion;
	private String estudiante;
	
	private String learningPath;


	

	public Feedback(String feedbackID, String comentario, int calificacion, String estudianteId,
		 String learningPath) {
		super();
		this.feedbackID = feedbackID;
		this.comentario = comentario;
		this.calificacion = calificacion;
		this.estudiante = estudianteId;
		
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
	

	public String getEstudiante() {
		return estudiante;
	}


	public String getLearningPath() {
		return learningPath;
	}

	public  Map<String, String > mostrarFeedback() {
		Map<String, String > map = new HashMap<>();
		map.put("feedbackID", feedbackID);
		map.put("ComentarioEstudiante", comentario);
		map.put("rating", Integer.toString(calificacion));
		map.put("Estudiante", estudiante);
		map.put("Learning Path", learningPath);
		
		return map;
	}
	
	public JSONObject toJSON() {
	    JSONObject objeto = new JSONObject();

	    objeto.put("feedbackID", feedbackID);
	    objeto.put("comentario", comentario);
	    objeto.put("calificacion", calificacion);
	    objeto.put("estudianteID", estudiante);
	    objeto.put("learningPathID", learningPath);

	    return objeto;
	}

}


