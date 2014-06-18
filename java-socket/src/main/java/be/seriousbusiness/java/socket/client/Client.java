package be.seriousbusiness.java.socket.client;

/**
 * Describes the functionality of a Client.
 * @author seriousbusiness
 * @author Stefan Borghys
 * 
 */
public interface Client extends Runnable {
	/**
	 * Get the client's port number.
	 * @return
	 */
	int getPort();
	
	/**
	 * Get the client's host ip.
	 * @return
	 */
	String getHost();
}
