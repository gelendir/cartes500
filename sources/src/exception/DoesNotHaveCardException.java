package exception;

/**
 * Exception lancé lorsqu'un joueur tente de déposer une carte
 * qu'il n'a pas dans sa main.
 *
 */
public class DoesNotHaveCardException extends GameException {
	
	public DoesNotHaveCardException() {
		super("Player does not have card in his hand");
	}
	
	public DoesNotHaveCardException(String message) {
		super(message);
	}
	
	

}
