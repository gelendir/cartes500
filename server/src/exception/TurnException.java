package exception;

/**
 * Exception lancé lorsqu'une erreur de jeu au niveau du tour
 * est détecté.
 *
 */
public class TurnException extends GameException {
	
	public TurnException(String message) {
		super(message);
	}

}
