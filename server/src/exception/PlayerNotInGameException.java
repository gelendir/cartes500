package exception;

public class PlayerNotInGameException extends GameException {

	public PlayerNotInGameException() {
		super("Player is not in the game");
	}

	public PlayerNotInGameException(String message) {
		super(message);
	}

}
