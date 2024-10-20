package Perisistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import Actividades.Actividad;

public class PersistenciaActividades implements IpersistenciaActividades {
	
	@Override
	public ArrayList<Actividad> cargarActividad(String archivo ) throws IOException {
		System.out.println("Cargando Actividades");
		String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
		JSONArray actividades = new JSONArray(jsonCompleto);
		ArrayList<String> quizes= new ArrayList<String>();
		ArrayList<String> tareas= new ArrayList<String>();
		ArrayList<String> examenes= new ArrayList<String>();
		ArrayList<String> encuestas= new ArrayList<String>();
		ArrayList<String> recursosEducativos= new ArrayList<String>();
		
		
		
		for (int i = 0; i < actividades.length(); i++) {
           JSONObject actividad = actividades.getJSONObject(i);
           String actividadID = actividad.get("actividadID").toString();
           String descripcion = actividad.get("descripcion").toString();
           String objetivo = actividad.get("objetivo").toString();
           int nivelDificultad = actividad.getInt("nivelDificultad");
           int duracionEsperada = actividad.getInt("duracionEsperada");
           boolean esObligatoria = actividad.getBoolean("esObligatoria");
           Date fechaLimite = new Date(actividad.getLong("fechaLimite"));
           String resenas = actividad.get("resenas").toString();
           double calificacion = actividad.getDouble("calificacion");
           int resultado = actividad.getInt("resultado");
           JSONArray actividadesPreviaArray = actividad.getJSONArray("actividadesPrevia");
           List<String> actividadesPrevia = new ArrayList<>();
			for (int j = 0; j < actividadesPreviaArray.length(); j++) {
				actividadesPrevia.add(actividadesPreviaArray.getString(j));
			}
			JSONArray actividadesSeguimientoArray = actividad.getJSONArray("actividadesSeguimiento");
			List<String> actividadesSeguimiento = new ArrayList<>();
			for (int j = 0; j < actividadesSeguimientoArray.length(); j++) {
				actividadesSeguimiento.add(actividadesSeguimientoArray.getString(j));
			}
			
			
			
		
		
		
		
		
		}
		
		return lista;
	}

	@Override
	public void salvarActividad(String archivo, String actividadID, String descripcion, String objetivo, int nivelDificultad,
			int duracionEsperada, boolean esObligatoria, Date fechaLimite2, String resenas, double calificacion,
			int resultado, List<String> actividadesPrevia, List<String> actividadesSeguimiento) {
		// TODO Auto-generated method stub

	}

}
