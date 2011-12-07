package exception;

public class ServerException extends RuntimeException {
	
	public ServerException() {
		super("A server exception has been thrown");
	}

	public ServerException(String message) {
		super(message);
	}

}
