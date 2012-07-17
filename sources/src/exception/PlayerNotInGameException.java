package exception;

/**
 * Exception lancé lorsqu'un joueur qui n'est pas connecté au serveur
 * tente d'accomplir une action.
 *
 */
public class PlayerNotInGameException extends GameException {

	public PlayerNotInGameException() {
		super("Player is not in the game");
	}

	public PlayerNotInGameException(String message) {
		super(message);
	}

}
