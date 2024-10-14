package Actividades;

import java.util.List;

public class Examen extends Actividades{

	public Examen(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, int fechaLimite, String resenas, double calificacion, int resultado) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				calificacion, resultado);
		// TODO Auto-generated constructor stub
	}

	public static final String EXAMEN = "Examen";
	public List<String> preguntas;

	@Override
	public String getTipoActividad() {
		return EXAMEN;
	
	}

	@Override
	public void agregarContenido(String pregunta) {
		preguntas.add(pregunta);

	
	}

	

	public String EstadoActual(String estado) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
