package exception;

/**
 * Exception parapluie utilisé lorsqu'un comportement du jeu
 * inattendu est servenu lorsque le serveur tente d'accomplir 
 * une action dans le jeu.
 *
 */
public class UnexpectedBehaviorException extends ServerException {

	public UnexpectedBehaviorException() {
		super("Unexpected behavior was encountered in the server");
	}

	public UnexpectedBehaviorException( Exception e ) {
		super("Unexpected behavior was encountered in the server");
		this.addSuppressed( e );
	}

}
