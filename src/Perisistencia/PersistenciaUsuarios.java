package Perisistencia;

import java.io.File;
import java.nio.file.Files;

import org.json.JSONArray;
import org.json.JSONObject;

import Usuarios.Usuario;



public class PersistenciaUsuarios {
	
	
	
	public void cargarUsuario(String archivo, String usuarioID) {
		
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
		JSONObject archivo1 = new JSONObject(jsonCompleto);
        
    }
    
	public void salvarUsuario(String archivo, String usuarioID) {
		
		

	}
    
	

}
