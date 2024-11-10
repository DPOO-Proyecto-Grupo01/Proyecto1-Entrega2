package Exceptions;

@SuppressWarnings("serial")
public class NombreRepetido extends Mensaje {

	public NombreRepetido(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMensaje() {
		return "El nombre ingresado ya existe";
	}
	
	

}
