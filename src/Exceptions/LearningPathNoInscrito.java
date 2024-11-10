
package Exceptions;

@SuppressWarnings("serial")
public class LearningPathNoInscrito extends Mensaje {

	// Constructor to initialize the exception with the custom message
	

	public LearningPathNoInscrito(String string) {
		super(string);
	
	}

	@Override
	public String getMensaje() {
		return "No estás inscrito en este learning path";
	}
}
