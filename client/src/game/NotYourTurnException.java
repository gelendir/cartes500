package game;

import exception.GameException;

public class NotYourTurnException extends GameException {

	public NotYourTurnException() {
		super("It is not your turn to play a card");
	}


}
