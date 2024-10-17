package LearningPaths;



import java.util.Date;

import Actividades.Actividad;
import Usuarios.Estudiante;

public class Progreso {
	
	    private String progresoID;
	    private Estudiante estudiante;
	    private LearningPath learningPath;
	    private Actividad actividad;
	    private Date fechaInicio;
	    private Date fechaCompletado;
	    private int tiempoDedicado;
	    private double estado;
	    public double porcentajeDeExito;
	
	    
	    
	    public Progreso(String progresoID, Estudiante estudiante, LearningPath learningPath, Actividad actividad, Date fechaInicio, Date fechaCompletado, int tiempoDedicado, double estado) {
	    	            this.progresoID = progresoID;
	    	            this.estudiante = estudiante;
	    	            this.learningPath = learningPath;
	    	            this.actividad = actividad;
	    	            this.fechaInicio = fechaInicio;
	    	            this.fechaCompletado = fechaCompletado;
	    	            this.tiempoDedicado = tiempoDedicado;
	    	            this.estado = estado;
	    	            
	    	            
	    }



		public String getProgresoID() {
			return progresoID;
		}



		public Estudiante getEstudiante() {
			return estudiante;
		}



		public LearningPath getLearningPath() {
			return learningPath;
		}



		public Actividad getActividad() {
			return actividad;
		}



		public Date getFechaInicio() {
			return fechaInicio;
		}



		public Date getFechaCompletado() {
			return fechaCompletado;
		}



		public int getTiempoDedicado() {
			return tiempoDedicado;
		}



		public double getEstado() {
			return estado;
		}


		public double getPorcentajeDeExito() {
			return porcentajeDeExito;
		}



		public void setPorcentajeDeExito(double porcentajeDeExito) {
			this.porcentajeDeExito = porcentajeDeExito;
		}



		
	    
	    
}
