package Actividades;

import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class RecursoEducativo extends Actividad {
	
	public static final String RECURSOEDUCATIVO = "Recurso Educativo";
	private String tipoRecurso;
	private String linkRecusro;
	private List<String> preguntas;
	private String tipo;
	
	
	public RecursoEducativo(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, int resultado, List<String> actividadesPrevias, 
			List<String> actividadesSeguimiento, String tiporecurso, String linkRecusro) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado,actividadesPrevias, actividadesSeguimiento, RECURSOEDUCATIVO);
		
		this.tipo=RECURSOEDUCATIVO;
		this.tipoRecurso = tiporecurso;
		this.linkRecusro = linkRecusro;

		
		
	}


	@Override
	public JSONObject convertToJSONObject() {
		return null;
	}

	@Override
	public String getTipoActividad() {
		return RECURSOEDUCATIVO;
	}
	
	@Override
	public void agregarContenido(String pregunta, List<String> opciones) {
		preguntas.add(pregunta);
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
