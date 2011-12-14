package exception;

/**
 * Exception lancé lorsqu'un joueur tente de jouer une carte
 * alors qu'il a déja joué lors du tour.
 *
 */
public class AlreadyPlayedException extends TurnException {

	public AlreadyPlayedException() {
		super("This player has already played a card for this turn");
	}
	
	public AlreadyPlayedException(String message) {
		super(message);
	}

}
