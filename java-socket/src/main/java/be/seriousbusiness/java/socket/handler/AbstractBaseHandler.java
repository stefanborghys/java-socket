package be.seriousbusiness.java.socket.handler;

import java.net.Socket;

import be.seriousbusiness.java.socket.protocol.Protocol;

public abstract class AbstractBaseHandler<M> implements Handler {
	protected final Socket socket;
	private final int port;
	private final String host;
	protected final Protocol<M> protocol;
	
	public AbstractBaseHandler(final Socket socket,final Protocol<M> protocol) throws IllegalArgumentException{
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
	public final int getClientPort() {
		return port;
	}

	@Override
	public final String getClientHost() {
		return host;
	}
	
	@Override
	public String toString(){
		return getClientHost() + ":" + getClientPort();
	}

}
