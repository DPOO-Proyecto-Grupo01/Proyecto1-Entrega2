package Actividades;

import java.util.Date;
import java.util.List;

public class RecursoEducativo extends Actividad {
	
	public static final String RECURSOEDUCATIVO = "Recurso Educativo";
	private String tipoRecurso;
	
	public RecursoEducativo(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, 
			List<String> actividadesPrevia, List<String> actividadesSeguimiento) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado, actividadesPrevia, actividadesSeguimiento);
		
		
	}
	
	
	@Override
	public String getTipoActividad() {
		return RECURSOEDUCATIVO;
	}
	
	@Override
	public void agregarContenido(String tipoRecurso) {
		// TODO Auto-generated method stub
	}


	public String getTipoRecurso() {
		return tipoRecurso;
	}
	
	public void setTipoRecurso(String tipoRecurso) {
		this.tipoRecurso = tipoRecurso;
	}


	
	
	

}
