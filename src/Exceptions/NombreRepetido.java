package Exceptions;

@SuppressWarnings("serial")
public class NombreRepetido extends Mensaje {

	public NombreRepetido(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	private String nombre;
	
	@Override
	public String getMensaje() {
		// TODO Auto-generated method stub
		return "El nombre"+ nombre + "esta repetido";
	}
	
	

}
