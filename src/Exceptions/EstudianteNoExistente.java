package Exceptions;

public class EstudianteNoExistente extends Mensaje{

	public EstudianteNoExistente(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getMensaje() {
		return "El estudiante no existe";
	}

}
