package be.seriousbusiness.java.socket.client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.seriousbusiness.java.socket.handler.Handler;
import be.seriousbusiness.java.socket.handler.factory.SocketHandlerFactory;
import be.seriousbusiness.java.socket.handler.factory.server.StringServerSocketHandlerFactory;
import be.seriousbusiness.java.socket.network.Port;

/**
 * Simple Socket Client implementation.
 * @author seriousbusiness
 * @author Stefan Borghys
 * 
 */
public class SocketClient extends Thread implements Client {
	private static final Logger LOGGER=LoggerFactory.getLogger(SocketClient.class);
	private final int serverPort;
	private int clientPort;
	private final String serverHost;
	private String clientHost;
	private final SocketHandlerFactory<?> socketHandlerFactory;
	
	/**
	 * Create a new SocketClient connecting to a running Server with host and port.
	 * @param serverHost the server host
	 * @param serverPort the server port number
	 * @param socketHandlerFactory
	 * @throws IllegalArgumentException when the port number is not between or equal to 0 and 65535.</br>
	 * When the host is <code>null</code> or empty or when the SocketHandlerFactory is <code>null</code>.
	 */
	public <C extends Handler> SocketClient(final String serverHost,final int serverPort,final SocketHandlerFactory<C> socketHandlerFactory) throws IllegalArgumentException{
		if(!Port.isValid(serverPort)){
			throw new IllegalArgumentException("Invalid server port number");
		}else if(serverHost==null || serverHost.isEmpty()){
			throw new IllegalArgumentException("The server host is null or empty");
		}else if(socketHandlerFactory==null){
			throw new IllegalArgumentException("SocketHandlerFactory is null");
		}
		this.serverPort=serverPort;
		this.socketHandlerFactory=socketHandlerFactory;
		this.serverHost=serverHost;
	}

	@Override
	public final int getPort() {
		return clientPort;
	}

	@Override
	public final String getHost() {
		return clientHost==null ? "" : clientHost;
	}
	
	@Override
	public void run(){
		try {
			final Socket socket=new Socket(serverHost,serverPort);
			new Thread(socketHandlerFactory.create(socket)).start();
			clientPort=socket.getLocalPort();
			clientHost=socket.getInetAddress().getHostName();
			LOGGER.info("Client {} started",this);
		} catch (final UnknownHostException e) {
			LOGGER.info("Client {} could not be started",this,e);
		} catch (final IOException e) {
			LOGGER.info("Client {} could not be started",this,e);
		}
	}
	
	@Override
	public String toString(){
		return serverHost + ":" + serverPort;
	}
	
	/**
	 * Start a SocketClient using String messages to communicate.
	 * @param args the host and port number are mandatory
	 */
	public static void main(final String[] args){
		if(args.length>=2){
			String host=args[0];
			int port=0;
			try{
				port=Integer.valueOf(args[1]);
				if(!Port.isValid(port)){
					LOGGER.error("The port number is not a valid number between 0 and 65535");
					return;
				}
			}catch(final NumberFormatException e){
				LOGGER.error("The port is not a number");
				return;
			}
			SocketClient socketClient=new SocketClient(host,port,new StringServerSocketHandlerFactory());
			socketClient.start();
		}
	}

}
