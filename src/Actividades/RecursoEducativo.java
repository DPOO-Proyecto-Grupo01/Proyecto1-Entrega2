package Actividades;

import java.util.Date;
import java.util.List;

public class RecursoEducativo extends Actividad {
	
	public static final String RECURSOEDUCATIVO = "Recurso Educativo";
	private String tipoRecurso;
	private String linkRecusro;
	
	public RecursoEducativo(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, 
			List<Actividad> actividadesPrevia, List<String> actividadesSeguimiento, String TIPORECURSO, String linkRecusro) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado, actividadesPrevia, actividadesSeguimiento, RECURSOEDUCATIVO);
		
		this.tipoRecurso = TIPORECURSO;
		this.linkRecusro = linkRecusro;
		
		
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


	public String getLinkRecusro() {
		return linkRecusro;
	}


	public void setLinkRecusro(String linkRecusro) {
		this.linkRecusro = linkRecusro;
	}

	public void completarRecurso() {
		System.out.println(this.linkRecusro);
	}

	
	
	

}
