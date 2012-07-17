package exception;

/**
 * Exception lancé lorsqu'un joueur envoie une mise alors que ce n'est pas
 * rendu à son tour de miser.
 *
 */
public class NotYourTurnToBet extends GameException {

	public NotYourTurnToBet() {
		super("It is not your turn to bet");
	}
	
	public NotYourTurnToBet(String message) {
		super(message);
	}

}
