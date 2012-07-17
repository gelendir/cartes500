package exception;

/**
 * Exception lancé lorsqu'un joueur dépose une carte qui est invalide.
 *
 */
public class InvalidCardException extends GameException {

	public InvalidCardException() {
		super("Playing Card does not exist.");
	}
	
	public InvalidCardException(String message) {
		super(message);
	}
}
