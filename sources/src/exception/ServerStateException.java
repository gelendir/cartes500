package exception;

/**
 * Exception lancé par le serveur lorsqu'un joueur essaye d'accomplir une action
 * alors que cette action n'est pas permis par l'état du serveur.
 * 
 */
public class ServerStateException extends RuntimeException {
	
	public ServerStateException(String message) {
		super(message);
	}

}
