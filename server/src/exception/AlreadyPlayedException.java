package exception;

public class AlreadyPlayedException extends TurnException {

	public AlreadyPlayedException() {
		super("This player has already played a card for this turn");
	}

}
