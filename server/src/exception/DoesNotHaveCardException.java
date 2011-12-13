package exception;


public class DoesNotHaveCardException extends GameException {
	
	public DoesNotHaveCardException() {
		super("Player does not have card in his hand");
	}
	
	public DoesNotHaveCardException(String message) {
		super("message");
	}
	
	

}
