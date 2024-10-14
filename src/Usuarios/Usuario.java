package Usuarios;

public abstract class Usuario {
	private String usuarioID;
	private String nombre;
	private String email;
	private String contraseña;
	
	public Usuario (String UsuarioID, String nombre, String contraseña, String email) {
		this.usuarioID= UsuarioID;
		
	}
	
	public abstract String getTipoUsuario();
		
	
	public boolean registrar() {
		return false;
		
	}
	public boolean iniciarSesion(String usuarioID, String contraseña) {
		return usuarioID.equals(this.usuarioID)&&contraseña.equals(contraseña);
		
	}
	
}