package Perisistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import Actividades.Actividad;
import Consola.ConsolaEstudiante;
import LearningPaths.LearningPath;
import LearningPaths.Progreso;
import Usuarios.Profesor;
 
public class PersistenciaProgreso {
	
	public List<Progreso> cargarProgreso(String archivo) throws Exception {
	   
	    String jsonCompleto = new String(Files.readAllBytes(new File(archivo).toPath()));
	    JSONArray progresos = new JSONArray(jsonCompleto);
	    ArrayList<Progreso> lista = new ArrayList<Progreso>();

	    for (int i = 0; i < progresos.length(); i++) {
			JSONObject progreso = progresos.getJSONObject(i);
			String progresoID = progreso.get("progresoID").toString();
			String estudianteID = progreso.get("estudianteID").toString();
			String learningPathID = progreso.get("learningPath").toString();
			String estado = progreso.get("estado").toString();
			int tiempoDedicado = progreso.getInt("tiempoDedicado");
			double porcentajeDeExito = progreso.getDouble("porcentajeDeExito");

			 Date fechaInicio = null;
		        Date fechaCompletado = null;
		        if (!progreso.isNull("fechaInicio")) {
		            fechaInicio = new Date(progreso.getLong("fechaInicio"));
		        }
		        if (!progreso.isNull("fechaCompletado")) {
		            fechaCompletado = new Date(progreso.getLong("fechaCompletado"));
		        }

	        Progreso nuevoProgreso = new Progreso(progresoID, estudianteID, learningPathID, 
	        		                 fechaInicio, fechaCompletado, tiempoDedicado, estado, porcentajeDeExito);
	        
	        lista.add(nuevoProgreso);
	    }
	    return lista;
	}
	
	public void actualizarProgreso(Progreso progreso) throws Exception {
		List<Progreso> listaProgresos = cargarProgreso(ConsolaEstudiante.getProgresoFile());
		boolean found = false;
		for (int i = 0; i < listaProgresos.size(); i++) {
			if (listaProgresos.get(i).getLearningPath().equals(progreso.getLearningPath())) {
				listaProgresos.set(i, progreso);
				found = true;
				break;
			}
		}
		if (!found) {
			listaProgresos.add(progreso);
		}
		salvarListaProgreso(listaProgresos);
	}

	public void salvarListaProgreso(List<Progreso> ListaProgreso) throws Exception {
		JSONArray jsonArray = new JSONArray();
		for (Progreso progreso : ListaProgreso) {
			jsonArray.put(progreso.toJSON());
		}
		try{
			Files.write(Paths.get(ConsolaEstudiante.getProgresoFile()), jsonArray.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

}

