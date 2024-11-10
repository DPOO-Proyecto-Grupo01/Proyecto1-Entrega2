package Exceptions;

@SuppressWarnings("serial")
public class YaSeCompleto extends Mensaje {
	
	
	//Quiero hacer esta excepción que salte cuando una persona quiere completar un learning path que ya se completó o una actividad
	
	//Para ello, quiero que el mensaje que salte sea "Ya se completó este learning path o actividad"
	
	public YaSeCompleto(String string) {
		super(string);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getMensaje() {
		return "Ya se completó este learning path o actividad";
	}
}
