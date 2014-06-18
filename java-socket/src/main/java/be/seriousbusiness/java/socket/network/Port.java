package be.seriousbusiness.java.socket.network;

/**
 * Port helper class, allowing to validate a port number.
 * @author seriousbusiness
 * @author Stefan Borghys
 * 
 */
public final class Port {
	public static final int MINIMUM=0,MAXIMUM=65535;
	
	/**
	 * Check if a port number is between the valid boundaries.
	 * @param port
	 * @return <code>true</code> when valid, otherwise <code>false</code>
	 */
	public static final boolean isValid(final int port){
		return port>=MINIMUM && port<=MAXIMUM;
	}

}
