package be.seriousbusiness.java.socket.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.seriousbusiness.java.socket.handler.Handler;
import be.seriousbusiness.java.socket.handler.factory.SocketHandlerFactory;
import be.seriousbusiness.java.socket.handler.factory.client.StringClientSocketHandlerFactory;
import be.seriousbusiness.java.socket.network.Port;

public class SocketServer extends Thread implements Server {
	private static final Logger LOGGER=LoggerFactory.getLogger(SocketServer.class);
	private final int port;
	private final SocketHandlerFactory<?> socketHandlerFactory;
	private String host;
	
	public <C extends Handler> SocketServer(final int port,final SocketHandlerFactory<C> socketHandlerFactory){
		if(Port.isValid(port)){
			this.port=port;
		}else{
			throw new IllegalArgumentException("Invalid port number");
		}
		if(socketHandlerFactory==null){
			throw new IllegalArgumentException("SocketHandlerFactory is null");
		}
		this.socketHandlerFactory=socketHandlerFactory;
	}

	@Override
	public final int getPort() {
		return port;
	}
	
	@Override
	public final String getHost() {
		return host==null ? "" : host;
	}

	@Override
	public void run() {
		try {
			final ServerSocket serverSocket=new ServerSocket(port);
			host=serverSocket.getInetAddress().getHostName();
			LOGGER.info("Server {} started",this);
			while(true){
				if(isInterrupted()){
					try{
						serverSocket.close();
					} catch (final IOException e){
						LOGGER.error("Server {} could not be stopped",this);
					}
					LOGGER.info("Server {} stopped",this);
					break;
				}else{
					LOGGER.debug("Server {} waiting...",this);
					final Socket socket = serverSocket.accept();
					if(socket!=null){
						new Thread(socketHandlerFactory.create(socket)).start();
					}
				}
			}
		} catch (final IOException e) {
			LOGGER.error("Server {} could not be started",this,e);
		} 
	}
	
	@Override
	public String toString(){
		return getHost() + ":" + port;
	}
	
	public static void main(final String[] args){
		final SocketServer socketServer=new SocketServer(3333,new StringClientSocketHandlerFactory());
		socketServer.start();
	}

}
