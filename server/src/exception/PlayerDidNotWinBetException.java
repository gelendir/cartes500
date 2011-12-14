package exception;

/**
 * Exception lancé lorsqu'un joueur envoie une nouvelle main
 * au serveur alors que ce n'est pas lui qui a remporté
 * la mise secrete.
 *
 */
public class PlayerDidNotWinBetException extends GameException {

	public PlayerDidNotWinBetException() {
		super("Player did not win the bet.");
	}

	public PlayerDidNotWinBetException(String message) {
		super(message);
	}

}
