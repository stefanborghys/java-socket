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

public class SocketClient extends Thread implements Client {
	private static final Logger LOGGER=LoggerFactory.getLogger(SocketClient.class);
	private final int serverPort;
	private int clientPort;
	private String serverHost;
	private String clientHost;
	private final SocketHandlerFactory<?> socketHandlerFactory;
	
	public <C extends Handler> SocketClient(final String serverHost,final int serverPort,final SocketHandlerFactory<C> socketHandlerFactory) throws IllegalArgumentException{
		if(Port.isValid(serverPort)){
			this.serverPort=serverPort;
		}else{
			throw new IllegalArgumentException("Invalid server port number");
		}
		if(serverHost==null || serverHost.isEmpty()){
			throw new IllegalArgumentException("The server host is null or empty");
		}
		if(socketHandlerFactory==null){
			throw new IllegalArgumentException("SocketHandlerFactory is null");
		}
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
	
	public static void main(final String[] args){
		SocketClient socketClient=new SocketClient("0.0.0.0",3333,new StringServerSocketHandlerFactory());
		socketClient.start();
	}

}
