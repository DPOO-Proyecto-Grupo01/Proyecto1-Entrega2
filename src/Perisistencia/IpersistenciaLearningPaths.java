package Perisistencia;

import java.io.IOException;
import java.util.List;

import LearningPaths.LearningPath;

public interface IpersistenciaLearningPaths {
	
	public List<LearningPath> cargarLearningPath(String archivo) throws  Exception;
	
	public void salvarLearningPath(String archivo, String LearningPathID, String titulo, String descripcion, String objetivos,
			int nivelDificultad, int duracion, String profesorID, List<String> actividadesID, List<String> intereses);

}
