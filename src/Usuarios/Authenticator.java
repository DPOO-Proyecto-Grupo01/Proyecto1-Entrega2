package Usuarios;
import java.util.List;

public class Authenticator  {
	
	private int codigo;
	private boolean fueAutenticado;
	private List<Usuario> usuarios;
	private String archivoUsuarios;

	// Constructor, getters and setters
	public Authenticator(int codigo, boolean fueAutenticado,
			List<Usuario> usuarios, String archivoUsuarios) {
		this.codigo = codigo;
		this.fueAutenticado = fueAutenticado;
		
		this.usuarios = usuarios;
		this.archivoUsuarios = archivoUsuarios;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public boolean isFueAutenticado() {
		return fueAutenticado;
	}

	public void setFueAutenticado(boolean fueAutenticado) {
		this.fueAutenticado = fueAutenticado;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public String getArchivoUsuarios() {
		return archivoUsuarios;
	}

	public void setArchivoUsuarios(String archivoUsuarios) {
		this.archivoUsuarios = archivoUsuarios;
	}
	
	public static boolean authenticate(String usuarioID, String contraseña, Usuario usuario) {
		return usuarioID.equals(usuario.getUsuarioID()) && contraseña.equals(usuario.getContraseña());
	}
	
	public void cambiarPassword(Usuario usuario, String contraseña) {
		usuario.setContraseña(contraseña);
	}

	
	
	
}


