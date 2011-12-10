package exception;

public class GameHasStartedException extends GameException {
	
	public GameHasStartedException() {
		super("The game has already started.");
	}
	
	public GameHasStartedException(String message) {
		super(message);
	}

}
