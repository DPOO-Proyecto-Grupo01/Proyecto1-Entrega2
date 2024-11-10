package Exceptions;

@SuppressWarnings("serial")
public class ActividadNoPertenece extends Mensaje {
	
	//Quiero hacer esta excepción que salte cuando una actividad no pertenece a un learning path y se quiere hacer
	
	//Para ello, quiero que el mensaje que salte sea "La actividad no pertenece a este learning path"
	
	
	
	public ActividadNoPertenece(String string) {
		super(string);
	}

	@Override
	public String getMensaje() {
		return "La actividad no pertenece a este learning path";
	}

}
