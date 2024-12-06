package LearningPaths;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import Actividades.Actividad;
import Actividades.Quiz;
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
	private List<String> actividadesID;
	private List<String> intereses = new ArrayList<>();
	public Map<String,Actividad> actividades = new HashMap<>();
	private List<Feedback> feedback = new ArrayList<>();
	private Profesor profesor;
	public String profesorID;
	public Progreso progreso;
	public Map<String, Progreso> progresoEstudiante= new HashMap<>();
	public HashMap<String, Estudiante> estudiantesInscritos = new HashMap<>();
	public static HashMap<String, LearningPath> learningPathsEstudiantes = new HashMap<>();
	private int exitosas;
	
	
//////////////////////
	
	
	public LearningPath(String LearningPathID, String titulo, String descripcion, String objetivos,
			int nivelDificultad, int duracion, String profesorID, List<String> actividadesID, List<String> intereses) {
		this.LearningPathID = LearningPathID;
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.objetivos = objetivos;
		this.nivelDificultad = nivelDificultad;
		this.duracionMinutos = duracion;
		this.profesorID = profesorID;
		this.actividadesID = actividadesID;
		this.intereses=intereses;
	
		
		
		}
	
		public void inscribirEstudiante(String estudiante, LearningPath learningpath) {
			learningPathsEstudiantes.put(estudiante, learningpath);
			
			
		}
		
		
	
	public List<String> getActividadesID() {
		return actividadesID;
	}



	public Progreso getProgreso() {
		return progreso;
	}

	public Map<String, Progreso> getProgresoEstudiante() {
		return progresoEstudiante;
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
	


	public Map<String,Actividad> getActividades() {
		return actividades;
	}


	public void setActividades(Map<String,Actividad> actividades) {
		this.actividades=actividades;
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
	
	
	public String getProfesorID() {
		return profesorID;
	}



	public Map<String,Actividad> obtenerActividades() {
		return actividades;
	}
	
	

	public static HashMap<String, LearningPath> getLearningPathsEstudiantes() {
		return learningPathsEstudiantes;
	}


	public double actualizarProgreso(Progreso progreso, List<Actividad> actividades) throws Exception {
		exitosas+=1;
		setProgreso(progreso);
		double porcentajeExitosas = ((double)exitosas/actividades.size());
		
		progreso.setPorcentajeDeExito(porcentajeExitosas);
	
		return porcentajeExitosas;
		
	}
	
	public Progreso obtenerProgresoEstudiante(String estudianteID) {
		System.out.print(progresoEstudiante);
		return progresoEstudiante.get(estudianteID);
		
	}

	public JSONObject toJSON() {
		// TODO Ver si falta algun atributo, yo segui lo que vi en learning_paths.json
		JSONObject objeto = new JSONObject();
		objeto.put("descripcion", descripcion);
		objeto.put("actividadesID", actividadesID);
		objeto.put("nivelDificultad", nivelDificultad);
		objeto.put("objetivos", objetivos);
		objeto.put("titulo", titulo);
		objeto.put("duracion", duracionMinutos);
		objeto.put("LearningPathID", LearningPathID);
		objeto.put("intereses", intereses);
		objeto.put("profesorID", profesorID);
		return objeto;
	}
	
	
	public String obtenerDetalles() {
		return "Titulo: " + titulo + "\n" + "Descripcion: " + descripcion + "\n" + "Objetivos: " + objetivos + "\n" + "Nivel de Dificultad: " + nivelDificultad + "\n" + "Duracion: " + duracionMinutos + "\n" 
	+ "Calificacion: " + calificacion + "\n" + "Fecha de Creacion: " + fechaCreacion + "\n" + "Fecha de Modificacion: " + fechaModificacion + "\n" + "Profesor: " + profesor;
		
	}
	
	public String inscripcionLearningPath() {
		return "Titulo" + titulo + "Descripcion" + descripcion + "Actividades" + actividades;
	}



	public List<String> getIntereses() {
		return this.intereses;
	}
	

}


