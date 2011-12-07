package exception;

public class UnexpectedBehaviorException extends ServerException {

	public UnexpectedBehaviorException() {
		// TODO Auto-generated constructor stub
	}

	public UnexpectedBehaviorException( Exception e ) {
		super("Unexpected behavior was encountered in the server");
		this.addSuppressed( e );
	}

}
