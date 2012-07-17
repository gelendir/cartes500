package exception;

/**
 * Exception lancé lorsqu'un joueur tente de se connecté au serveur
 * alors qu'un autre joueur avec le même profil est déja connecté.
 *
 */
public class AlreadyConnectedException extends ServerException {
	
	public AlreadyConnectedException() {
		super("This player is already connected to the server");
	}
	
	public AlreadyConnectedException(String message) {
		super(message);
	}

}
