package be.seriousbusiness.java.socket.handler;

import java.net.Socket;

import be.seriousbusiness.java.socket.protocol.Protocol;

/**
 * Generic abstract Socket Handler implementation.</br>
 * Implements the mandatory storage of a Socket instance and the Protocol to use for it.
 * @author seriousbusiness
 * @author Stefan Borghys
 * 
 * @param <M> the message type
 */
public abstract class AbstractSocketHandler<M> implements Handler {
	protected final Socket socket;
	private final int port;
	private final String host;
	protected final Protocol<M> protocol;
	
	/**
	 * Create a new AbstractSocketHandler usable for extending classes.
	 * @param socket the socket to handle
	 * @param protocol the protocol to use
	 * @throws IllegalArgumentException when the socket or protocol is <code>null</code>
	 * 
	 */
	public AbstractSocketHandler(final Socket socket,final Protocol<M> protocol) throws IllegalArgumentException{
		if(socket==null){
			throw new IllegalArgumentException("The socket is null");
		}else if(protocol==null){
			throw new IllegalArgumentException("The protocol is null");
		}
		this.socket=socket;
		this.port=socket.getPort();
		this.host=socket.getInetAddress().getHostName();
		this.protocol=protocol;
	}

	@Override
	public final int getPort() {
		return port;
	}

	@Override
	public final String getHost() {
		return host;
	}
	
	@Override
	public String toString(){
		return getHost() + ":" + getPort();
	}

}
