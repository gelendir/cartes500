package exception;

/**
 * Exception lancé lorsqu'un joueur tente de se connecter au serveur
 * alors que la partie a déja commencé.
 *
 */
public class GameHasStartedException extends GameException {
	
	public GameHasStartedException() {
		super("The game has already started.");
	}
	
	public GameHasStartedException(String message) {
		super(message);
	}

}
