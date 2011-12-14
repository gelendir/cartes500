package exception;

/**
 * Exception lancé lorsqu'un joueur tente de jouer une carte
 * alors que ce n'est pas son tour.
 *
 */
public class NotYourTurnException extends GameException {
	
	public NotYourTurnException() {
		super("It is not your turn to play a card");
	}
	
	public NotYourTurnException(String message) {
		super(message);
	}

}
