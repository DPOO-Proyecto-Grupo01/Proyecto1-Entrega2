package Exceptions;

@SuppressWarnings("serial")
public class LearningPathNoInscrito extends Mensaje{

	
	
	//Quiero implementar esta excepción que salta cuando una persona quiere completar un learning path que no está inscrito
	//Para ello, quiero que el mensaje que salte sea "No estás inscrito en este learning path"
	//Además, quiero que esta excepción herede de la clase Mensaje, que contiene el método getMensaje
	
	@Override
	public String getMensaje() {
		return "No estás inscrito en este learning path";
	}
	
	
	
	
	

}
