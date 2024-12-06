package LearningPaths;



import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import Actividades.Actividad;
import Perisistencia.PersistenciaProgreso;
import Usuarios.Estudiante;

public class Progreso {
	
	    private String progresoID;
	    private String estudianteID;
	    private String learningPathID;
	    private Actividad actividad;
	    private Date fechaInicio;
	    private Date fechaCompletado;
	    private int tiempoDedicado;
	    private String estado;
	    public double porcentajeDeExito;

	    
	    
	    public Progreso(String progresoID, String estudianteID, String learningPathID, Date fechaInicio, 
	    		Date fechaCompletado, int tiempoDedicado, String estado, double porecntajeDeExito) {
	    	            this.progresoID = progresoID;
	    	            this.estudianteID = estudianteID;
	    	            this.learningPathID = learningPathID;
	    	            this.fechaInicio = fechaInicio;
	    	            this.fechaCompletado = fechaCompletado;
	    	            this.tiempoDedicado = tiempoDedicado;
	    	            this.estado = estado;
	    	            this.porcentajeDeExito = porcentajeDeExito;
	    	            
	    	            
	    }


		public String getProgresoID() {
			return progresoID;
		}



		public String getEstudiante() {
			return estudianteID;
		}



		public String getLearningPath() {
			return learningPathID;
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



		public String getEstado() {
			return estado;
		}
		
		public void setEstado(String estado) {
			this.estado = estado;
		}


		public double getPorcentajeDeExito() {
			return porcentajeDeExito;
		}



		public void setPorcentajeDeExito(double porcentajeDeExito) throws Exception {
			PersistenciaProgreso persistenciaProgreso = new PersistenciaProgreso();
			persistenciaProgreso.actualizarProgreso(this);
			this.porcentajeDeExito = porcentajeDeExito;
			
		}
		
		//Hacer una funcion para actualizar progreso
		

		public  Map<String, String > mostrarProgreso() {
			Map<String, String > map = new HashMap<>();
			map.put("ProgresoID", progresoID);
			map.put("EstudianteID", estudianteID);
			map.put("Fecha Inicio", fechaInicio.toString());
			map.put("Estado", estado);
			map.put("Porcentaje de Exito", Double.toString(porcentajeDeExito));
			
			return map;
		}


		public JSONObject toJSON() {
		    JSONObject objeto = new JSONObject();

		    objeto.put("progresoID", progresoID);
		    objeto.put("estudianteID", estudianteID);
		    objeto.put("estado", estado);
		    objeto.put("tiempoDedicado", tiempoDedicado);
		    objeto.put("porcentajeDeExito", porcentajeDeExito);
		    objeto.put("fechaInicio", fechaInicio != null ? fechaInicio.getTime() : JSONObject.NULL);
		    objeto.put("fechaCompletado", fechaCompletado != null ? fechaCompletado.getTime() : JSONObject.NULL);
		    objeto.put("learningPath", learningPathID);
		    if (actividad != null) {
		        objeto.put("actividad", actividad.toJSON());
		    } else {
		        objeto.put("actividad", JSONObject.NULL);
		    }

		    return objeto;
		}
	    
	    
}
