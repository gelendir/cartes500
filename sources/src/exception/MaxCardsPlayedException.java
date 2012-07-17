package exception;

/**
 * Exception lancé lorsqu'un joueur dépose une carte alors que le 
 * maximum de cartes permis lors d'un tour est atteint.
 *
 */
public class MaxCardsPlayedException extends TurnException {

	public MaxCardsPlayedException() {
		super("Cannot play anymore cards during this turn");
	}
	
	public MaxCardsPlayedException(String message) {
		super(message);
	}

}
