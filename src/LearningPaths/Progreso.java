package LearningPaths;



import java.util.Date;

import Actividades.Actividades;
import Usuarios.Estudiante;

public class Progreso {
	
	    private String progresoID;
	    private Estudiante estudiante;
	    private LearningPath learningPath;
	    private Actividades actividad;
	    private Date fechaInicio;
	    private Date fechaCompletado;
	    private int tiempoDedicado;
	    private double estado;
	    private double tasaÉxito;
	
	    
	    
	    public Progreso(String progresoID, Estudiante estudiante, LearningPath learningPath, Actividades actividad, Date fechaInicio, Date fechaCompletado, int tiempoDedicado, double estado, double tasaÉxito) {
	    	            this.progresoID = progresoID;
	    	            this.estudiante = estudiante;
	    	            this.learningPath = learningPath;
	    	            this.actividad = actividad;
	    	            this.fechaInicio = fechaInicio;
	    	            this.fechaCompletado = fechaCompletado;
	    	            this.tiempoDedicado = tiempoDedicado;
	    	            this.estado = estado;
	    	            this.tasaÉxito = tasaÉxito;
	    	            
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



		public Actividades getActividad() {
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



		public double getTasaÉxito() {
			return tasaÉxito;
		}
		
		public void registrarProgreso(Estudiante estudiante, LearningPath learningPath, Actividades actividad, Date fechaInicio, Date fechaCompletado, int tiempoDedicado, double estado, double tasaÉxito) {
			
			
			
			

		}
	    
	    
}
