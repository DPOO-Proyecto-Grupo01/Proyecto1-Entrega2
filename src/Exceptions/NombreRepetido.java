package Exceptions;

@SuppressWarnings("serial")
public class NombreRepetido extends Mensaje {

	private String nombre;
	
	@Override
	public String getMensaje() {
		// TODO Auto-generated method stub
		return "El nombre"+ nombre + "esta repetido";
	}
	
	

}
