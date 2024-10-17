package Perisistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;


import org.json.JSONObject;
import org.json.JSONArray;


import Usuarios.Usuario;



public class PersistenciaUsuarios implements IpersistenciaUsuarios {
	
	
	
	public void cargarUsuario(String archivo, Usuario usuarioID) throws Exception {
		
		System.out.println("Cargando usuario");
		
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
		JSONObject archivo1 = new JSONObject(jsonCompleto);
		JSONArray usuarios = archivo1.getJSONArray("usuarios");
		for(int i = 0; i < usuarios.length(); i++) {
			JSONObject usuario = usuarios.getJSONObject(i);
			if (usuario.getString("usuarioID").equals(usuarioID)) {
				
			}
				
		
		}	
    }
    
	public void salvarUsuario(String archivo, Usuario usuarioID) {
		
		

	}
    
	

}
