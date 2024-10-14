package Usuarios;

public abstract class Usuario {
	private String usuarioID;
	private String nombre;
	private String email;
	private String contraseña;
	
	public Usuario (String UsuarioID, String nombre, String contraseña, String email) {
		this.usuarioID= UsuarioID;
		this.nombre= nombre;
		this.email= email;
		this.contraseña= contraseña;
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



	public String getContraseña() {
		return contraseña;
	}
	
	public abstract String getTipoUsuario();
	
	
	public boolean iniciarSesion(String usuarioID, String contraseña) {
		boolean sentinela = true;
		if (Authenticator.authenticate(usuarioID, contraseña, this)) {
			System.out.println("Inicio de sesion exitoso");
			sentinela = true;
		} else {
			System.out.println("Inicio de sesion fallido");
			sentinela = false;
		}
		return sentinela;
		
		
	}
	
	
	
	
	
}