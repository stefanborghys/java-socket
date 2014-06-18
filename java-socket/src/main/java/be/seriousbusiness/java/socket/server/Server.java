package be.seriousbusiness.java.socket.server;

/**
 * When started accessible from a fixed address (host and port).</br>
 * Serving incoming client requests 
 * @author seriousbusiness
 * @author Stefan Borghys
 * 
 */
public interface Server extends Runnable {
	/**
	 * Get the server's port number.
	 * @return
	 */
	int getPort();
	
	/**
	 * Get the server's host ip.
	 * @return
	 */
	String getHost();
}
