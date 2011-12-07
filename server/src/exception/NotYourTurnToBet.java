package exception;

public class NotYourTurnToBet extends GameException {

	public NotYourTurnToBet() {
		super("It is not your turn to bet");
	}

}
