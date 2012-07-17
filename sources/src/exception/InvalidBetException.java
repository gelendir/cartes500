package exception;

/**
 * Exception lancé lorsqu'un joueur envoie une mise qui est invalide.
 *
 */
public class InvalidBetException extends GameException {
	
	public InvalidBetException() {
		super("The bet is invalid");
	}

	public InvalidBetException(String message) {
		super(message);
	}
}
