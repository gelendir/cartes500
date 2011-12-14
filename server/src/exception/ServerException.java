package exception;

/**
 * Exception générique lancé pour tout erreur survenant au niveau du serveur.
 *
 */
public class ServerException extends RuntimeException {
	
	public ServerException() {
		super("A server exception has been thrown");
	}

	public ServerException(String message) {
		super(message);
	}

}
