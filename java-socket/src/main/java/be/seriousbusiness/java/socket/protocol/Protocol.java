package be.seriousbusiness.java.socket.protocol;

/**
 * Describes a simple client server protocol allowing to interact messages between each other.
 * @author seriousbusiness
 * @author Stefan Borghys
 *
 * @param <M> the type of message
 */
public interface Protocol<M> {
	/**
	 * First message send by the Server.
	 * @return
	 */
	M begin();
	
	/**
	 * Retrieve the server's state by analyzing his respond.
	 * @param message server message
	 * @return
	 */
	State serverRespond(final M message);
	
	/**
	 * Create a proper client respond to a server's message. 
	 * @param message server message
	 * @return client message
	 */
	M respondServer(final M message);
	
	/**
	 * Retrieve the client's state by analyzing his respond.
	 * @param message client message
	 * @return
	 */
	State clientRespond(final M message);
	
	/**
	 * Create a proper server respond to a client's message.
	 * @param message client message
	 * @return 
	 */
	M respondClient(final M message);
}
