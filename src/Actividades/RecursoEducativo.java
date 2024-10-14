package Actividades;

public class RecursoEducativo extends Actividades {
	
	public static final String RECURSOEDUCATIVO = "Recurso Educativo";
	private String tipoRecurso;
	
	public RecursoEducativo(String actividadID, String descripcion, String objetivo, int nivelDificultad,
			int duracionEsperada, boolean esObligatoria, int fechaLimite, String resenas, double calificacion,
			int resultado, String tipoRecurso) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado);
		this.tipoRecurso = tipoRecurso;
		
	}
	
	
	@Override
	public String getTipoActividad() {
		return RECURSOEDUCATIVO;
	}
	
	@Override
	public void agregarContenido(String tipoRecurso) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public String EstadoActual(String estado) {
		return estado;
		// TODO Auto-generated method stub
		
	}


	public String getTipoRecurso() {
		return tipoRecurso;
	}


	
	
	

}
