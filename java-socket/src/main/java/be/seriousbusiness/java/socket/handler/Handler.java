package be.seriousbusiness.java.socket.handler;

/**
 * Describes a Handler, responsible for handling the communication of a Client or Server instance.
 * @author seriousbusiness
 * @author Stefan Borghys
 * 
 */
public interface Handler extends Runnable {
	/**
	 * Get the port number of the instance being handled. 
	 * @return
	 */
	int getPort();
	
	/**
	 * Get the host ip of the instance being handled.
	 * @return
	 */
	String getHost();
}
