package exception;

public class MaxCardsPlayedException extends TurnException {

	public MaxCardsPlayedException() {
		super("Cannot play anymore cards during this turn");
	}

}
