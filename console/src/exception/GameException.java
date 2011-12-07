package exception;

public class GameException extends Exception {

	public GameException() {
		super("A game exception has been thrown");
	}

	public GameException(String message) {
		super(message);
	}
}
