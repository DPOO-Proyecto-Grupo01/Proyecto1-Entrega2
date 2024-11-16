package Usuarios;

import java.util.HashMap;
import java.util.List;

public abstract class Usuario {
	protected String usuarioID;
	private String nombre;
	private String email;
	private String contraseña;
	private String tipoUsuario;
	public static final String estudiante = "Estudiante";
	public static final String profesor = "Profesor";
	private static HashMap<String, Profesor> profesores = new HashMap<>();
	
	public Usuario (String UsuarioID, String nombre, String contraseña, String email, String tipoUsuario) {
		this.usuarioID= UsuarioID;
		this.nombre= nombre;
		this.email= email;
		this.contraseña= contraseña;
		this.tipoUsuario= tipoUsuario;
	}
	
	
	public String getTipoUsuario1() {
		return this.tipoUsuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public String getUsuarioID() {
		return usuarioID;
	}

	public String getNombre() {
		return nombre;
	}

	public String getEmail() {
		return email;
	}


	public abstract String getTipoUsuario();
	
	
	public boolean iniciarSesion(String usuarioID, String contraseña) {
		boolean centinela = true;
		if (Authenticator.authenticate(usuarioID, contraseña, this)) {
			centinela = true;
		} else {
			centinela = false;
		}
		return centinela;
		
		
	}
	
	//Quiero crear un nuevo usuario, puede ser estudiante o profesor, pero le pida confirmar contraseña

	public void anadirUsuario(List<Usuario> usuarios, Usuario usuario) {
		usuarios.add(usuario);
    }

	public static HashMap<String, Profesor> getProfesores() {
		return profesores;
	}
	
	
	
	
	
	
}