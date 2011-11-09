package exception;

public class InvalidCardException extends GameException {

	public InvalidCardException() {
		super("Playing Card does not exist.");
	}
}
