package exception;

public class GameException extends Exception {

	public GameException() {
		super("Une exception à propos du jeu a été levée.");
	}

	public GameException(String message) {
		super(message);
	}
}
