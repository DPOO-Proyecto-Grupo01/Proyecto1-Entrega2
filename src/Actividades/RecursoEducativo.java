package Actividades;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class RecursoEducativo extends Actividad {
	
	public static final String RECURSOEDUCATIVO = "Recurso Educativo";
	private String tipoRecurso;
	private String linkRecusro;
	private String tipo;
	
	
	public RecursoEducativo(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion, double resultado, List<String> actividadesPrevias, 
			List<String> actividadesSeguimiento, String tiporecurso, String linkRecusro) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				actividadesPrevias, actividadesSeguimiento, RECURSOEDUCATIVO);
		
		this.tipo=RECURSOEDUCATIVO;
		this.tipoRecurso = tiporecurso;
		this.linkRecusro = linkRecusro;

		
	} 


	@Override
	public JSONObject convertToJSONObject() {
		// Valores genericos de actividad
		JSONObject newObject = new JSONObject();
		newObject.put("actividadID", actividadID);
		newObject.put("descripcion", descripcion);
		newObject.put("objetivo", objetivo);
		newObject.put("nivelDificultad", nivelDificultad);
		newObject.put("duracionEsperada", duracionEsperada);
		newObject.put("esObligatoria", esObligatoria);
		newObject.put("fechaLimite", fechaLimite.getTime());
		newObject.put("resenas", resenas);
		newObject.put("calificacion", calificacion);
		newObject.put("resultado", resultado);
		newObject.put("actividadesPrevias", actividadesPrevias);
		newObject.put("actividadesSeguimiento", actividadesSeguimiento);
		newObject.put("tipoActividad", tipoActividad);

		//Valores especificos de la clase
		newObject.put("tiporecurso", tipoRecurso);
		newObject.put("linkRecurso", linkRecusro);
		
		return newObject;

	}

	@Override
	public String getTipoActividad() {
		return RECURSOEDUCATIVO;
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


	@Override
	public void agregarContenido(String pregunta, List<String> opciones) {
		

	}

	
	
	

}
