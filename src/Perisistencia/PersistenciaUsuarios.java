package Perisistencia;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;


import org.json.JSONObject;

import LearningPaths.LearningPath;

import org.json.JSONArray;

import Usuarios.Estudiante;
import Usuarios.Profesor;
import Usuarios.Usuario;



public class PersistenciaUsuarios implements IpersistenciaUsuarios {
	
    private static PersistenciaLearningPaths persistenciaLearningPaths = new PersistenciaLearningPaths();
	
	
	public ArrayList<Estudiante> cargarEstudiantes(String archivo) throws Exception {
		
		
		
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
		JSONArray usuarios = new JSONArray(jsonCompleto);
		ArrayList<Estudiante> estudiantes = new ArrayList<Estudiante>();
		for(int i = 0; i < usuarios.length(); i++) {
			JSONObject usuario = usuarios.getJSONObject(i);
				String usuarioID1 = usuario.get("usuarioID").toString();
				String nombre = usuario.get("nombre").toString();
				String contraseña = usuario.get("contrasena").toString();
				String email = usuario.get("email").toString();
				String tipoUsuario = usuario.get("tipoUsuario").toString();	
				
				if(tipoUsuario.equals("Estudiante")){
					Usuario estudiante = new Estudiante(usuarioID1, nombre, contraseña, email,
							tipoUsuario);
					estudiantes.add((Estudiante) estudiante);
				}
				
				
			
			}
		
		return estudiantes; 
				
		
			
    }	
	
	public ArrayList<Profesor> cargarProfesores(String archivo) throws Exception {
		
		
		String jsonCompleto = new String( Files.readAllBytes( new File( archivo ).toPath( ) ) );
		JSONArray usuarios = new JSONArray(jsonCompleto);
		ArrayList<Profesor> profesores = new ArrayList<Profesor>();
		for(int i = 0; i < usuarios.length(); i++) {
			JSONObject usuario = usuarios.getJSONObject(i);
				String usuarioID1 = usuario.get("usuarioID").toString();
				String nombre = usuario.get("nombre").toString();
				String contraseña = usuario.get("contrasena").toString();
				String email = usuario.get("email").toString();
				String tipoUsuario = usuario.get("tipoUsuario").toString();	
				if(tipoUsuario.equals("Profesor")){
					Usuario profesor = new Profesor(usuarioID1, nombre, contraseña, email,
							tipoUsuario);
					profesores.add((Profesor) profesor);
					profesor.getProfesores().put(usuarioID1, (Profesor) profesor);
				}
				
				
			}
		
		return profesores; 
				
		
			
    }	
	
   

	@Override
	public void salvarEstudiante(String archivo, String UsuarioID, String nombre, String contraseña, String email, String tipoUsuario) {
	    try {
	        // Read the content of the file
	        String content = new String(Files.readAllBytes(Paths.get(archivo)));
	       

	        // Parse the content to a JSON array
	        JSONArray jsonArray = new JSONArray(content);
	        

	        // Create a new JSON object for the new student
	        JSONObject newObject = new JSONObject();
	        newObject.put("usuarioID", UsuarioID);
	        newObject.put("nombre", nombre);
	        newObject.put("contrasena", contraseña);
	        newObject.put("email", email);
	        newObject.put("tipoUsuario", tipoUsuario);

	        // Add the new object to the JSON array
	        jsonArray.put(newObject);
	        

	        // Write the updated JSON array back to the file
	        Files.write(Paths.get(archivo), jsonArray.toString().getBytes());
	        
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void salvarProfesor(String archivo, String UsuarioID, String nombre, String contraseña, String email, String tipoUsuario) {
		
		        try {
            // Read the content of the file
            String content = new String(Files.readAllBytes(Paths.get(archivo)));
           

            // Parse the content to a JSON array
            JSONArray jsonArray = new JSONArray(content);
            

            // Create a new JSON object for the new student
            JSONObject newObject = new JSONObject();
            newObject.put("usuarioID", UsuarioID);
            newObject.put("nombre", nombre);
            newObject.put("contrasena", contraseña);
            newObject.put("email", email);
            newObject.put("tipoUsuario", tipoUsuario);

            // Add the new object to the JSON array
            jsonArray.put(newObject);
          

            // Write the updated JSON array back to the file
            Files.write(Paths.get(archivo), jsonArray.toString().getBytes());
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    

	}
	
	
	public void cargarLearningPathsProfesor(List<LearningPath> learningPaths, Map<String, Profesor> profesoresParametro ) throws Exception {
		for (Profesor profesor : profesoresParametro.values()) {
			Map<String, LearningPath> lpProfesorActual = new HashMap<>();
			
			for (LearningPath lp : learningPaths) {
				
				if (lp.getProfesorID().equals(profesor.getUsuarioID())){
					System.out.println("El profesor: "+ profesor.getUsuarioID() + " tiene el learning path: "+ lp.getLearningPathID());
					lpProfesorActual.put(lp.getLearningPathID(),lp);
				}
			
			}
			
			profesor.setLearningPathsCreados(lpProfesorActual);

		}
		
		
	}
	

}
