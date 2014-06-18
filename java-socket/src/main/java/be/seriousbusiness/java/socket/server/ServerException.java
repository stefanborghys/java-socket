package be.seriousbusiness.java.socket.server;

/**
 * Thrown when a Server cannot be started.
 * @author seriousbusiness
 *
 */
public class ServerException extends RuntimeException  {
	private static final long serialVersionUID = 3472874439222534020L;

	/**
	 * Create a new ServerException.
	 * @param message
	 */
	public ServerException(final String message){
		super(message);
	}
}
