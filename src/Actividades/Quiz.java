package Actividades;

public class Quiz extends Actividad {

	public Quiz(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, int fechaLimite, String resenas, double calificacion, int resultado) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado);
		// TODO Auto-generated constructor stub
	}
	
	public static final String QUIZ = "Quiz";
	private double calificacionMinima;
	
	

	public double getCalificacionMinima() {
		return calificacionMinima;
	}

	public void setCalificacionMinima(double calificacionMinima) {
		this.calificacionMinima = calificacionMinima;
	}

	@Override
	public String getTipoActividad() {
		return QUIZ;
	}

	@Override
	public void agregarContenido(String pregunta) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String EstadoActual(String estado) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
