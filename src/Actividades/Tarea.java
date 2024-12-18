package Actividades;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class Tarea extends Actividad {
	
	private String estado;
	private String instrucciones;
	private List<Pregunta> preguntas;
	private Collection<List<String>> opciones;

	public Tarea(String actividadID, String descripcion, String objetivo, int nivelDificultad, int duracionEsperada,
			boolean esObligatoria, Date fechaLimite, String resenas, double calificacion,double resultado,List<String> actividadesPrevias,  
			List<String> actividadesSeguimiento, List<Pregunta> preguntas, String instrucciones, String estado) {
		super(actividadID, descripcion, objetivo, nivelDificultad, duracionEsperada, esObligatoria, fechaLimite, resenas,
				actividadesPrevias, actividadesSeguimiento, TAREA);

		this.estado = estado;
		this.instrucciones = instrucciones;
		this.preguntas = preguntas;	
		
	

	}
	
	public void getOpciones(Map<String, List<String>> preguntas) {
		this.opciones = preguntas.values();
		
	}

	@Override
	public JSONObject convertToJSONObject() {
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
        newObject.put("instrucciones", instrucciones);
        
		JSONArray preguntasArray = new JSONArray();
		for (Pregunta pregunta : preguntas) {
			JSONObject preguntaJSON = new JSONObject();
			preguntaJSON.put("pregunta", pregunta.pregunta);

			JSONArray opcionesArray = new JSONArray();
			for (String opcion : pregunta.opciones) {
				opcionesArray.put(opcion);
			}
			preguntaJSON.put("opciones", opcionesArray);

			preguntasArray.put(preguntaJSON);
		}
        
        newObject.put("preguntas", preguntas);
        newObject.put("opciones", opciones);
        
		if (estado != null) {
			newObject.put("estado", estado);
		}
		else {
			newObject.put("estado", "null");

		}
        return newObject;
		} 


	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	public String getObjetivo() {
		return objetivo;
	}



	public void setObjetivo(String objetivo) {
		this.objetivo = objetivo;
	}



	public int getDuracionEsperada() {
		return duracionEsperada;
	}



	public void setDuracionEsperada(int duracionEsperada) {
		this.duracionEsperada = duracionEsperada;
	}



	public Date getFechaLimite() {
		return fechaLimite;
	}



	public void setFechaLimite(Date fechaLimite) {
		this.fechaLimite = fechaLimite;
	}



	public String getActividadID() {
		return actividadID;
	}



	public int getNivelDificultad() {
		return nivelDificultad;
	}



	public boolean isEsObligatoria() {
		return esObligatoria;
	}



	public String getResenas() {
		return resenas;
	}



	public double getCalificacion() {
		return calificacion;
	}



	public int getResultado() {
		return resultado;
	}
	

	@Override
	public String getTipoActividad() {
		return TAREA;
	}


	@Override
	public void agregarContenido(String pregunta, List<String> opciones) {
		this.preguntas.add(new Pregunta(pregunta, opciones));
	}

	public String getInstrucciones() {
		return instrucciones;
	}
	
	public void setInstrucciones(String Instrucciones) {
		this.instrucciones = Instrucciones;
	}
	
	public String registrarEntrega(String medioEntrega) {
		return medioEntrega; 
		
	}

	public List<Pregunta> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<Pregunta> preguntas) {
		this.preguntas = preguntas;
	}
	
	
	public void completarTarea() {
		System.out.println(this.preguntas);
	}

	
	
	

}
