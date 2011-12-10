package exception;

public class PlayerDidNotWinBetException extends GameException {

	public PlayerDidNotWinBetException() {
		super("Player did not win the bet.");
	}

	public PlayerDidNotWinBetException(String message) {
		super(message);
	}

}
