package exception;

public class EmptyDeckException extends GameException {

	public EmptyDeckException() {
		super("The deck is empty.");
	}
}
