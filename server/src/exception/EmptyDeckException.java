package exception;

/**
 * Exception lancé lorsqu'il n'y a plus de cartes dans la
 * pile de cartes.
 *
 */
public class EmptyDeckException extends GameException {

	public EmptyDeckException() {
		super("The deck is empty.");
	}
	
	public EmptyDeckException(String message) {
		super(message);
	}
}
