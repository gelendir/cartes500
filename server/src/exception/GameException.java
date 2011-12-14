package exception;

/**
 * Exception lancé lorsqu'une erreur au niveau de la logique du jeu 
 * est détecté.
 *
 */
public class GameException extends Exception {

	public GameException() {
		super("A game exception has been thrown");
	}

	public GameException(String message) {
		super(message);
	}
}
