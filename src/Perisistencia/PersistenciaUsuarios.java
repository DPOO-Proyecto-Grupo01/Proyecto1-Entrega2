package Perisistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.json.JSONObject;
import org.json.JSONArray;

import Usuarios.Usuario;



public class PersistenciaUsuarios implements IpersistenciaUsuarios {
	
	
	
	public void cargarUsuario(String archivo, String usuarioID) throws Exception {
		
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
		JSONObject archivo1 = new JSONObject(jsonCompleto);
		String usuarioID1 = archivo1.getString("usuarioID");
		String nombre = archivo1.getString("nombre");
		
		System.out.println(usuarioID1);
		System.out.println(nombre);
        
    }
    
	public void salvarUsuario(String archivo, String usuarioID) {
		
		

	}
    
	

}
