package exception;

public class InvalidBetException extends GameException {
	
	public InvalidBetException() {
		super("The bet is invalid");
	}

	public InvalidBetException(String message) {
		super(message);
	}
}
