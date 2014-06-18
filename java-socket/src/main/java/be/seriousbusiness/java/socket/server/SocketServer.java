package be.seriousbusiness.java.socket.server;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.seriousbusiness.java.socket.handler.Handler;
import be.seriousbusiness.java.socket.handler.factory.SocketHandlerFactory;
import be.seriousbusiness.java.socket.handler.factory.client.StringClientSocketHandlerFactory;
import be.seriousbusiness.java.socket.network.Port;

/**
 * Basic Socket Server implementation.
 * @author seriousbusiness
 * @author Stefan Borghys
 * 
 */
public class SocketServer extends Thread implements Server {
	private static final Logger LOGGER=LoggerFactory.getLogger(SocketServer.class);
	private final int port;
	private final SocketHandlerFactory<?> socketHandlerFactory;
	private String host;
	
	/**
	 * Creates a new Server using sockets to communicate.</br>
	 * </br>
	 * @param port a number between or including 0 and 65535.</br>
	 * Not used by any other application.
	 * @param socketHandlerFactory
	 * @throws IllegalArgumentException when the port is not between 0 and 65535</br>
	 * or when the sockketHandlerFactory is null.
	 */
	public <C extends Handler> SocketServer(final int port,final SocketHandlerFactory<C> socketHandlerFactory) throws IllegalArgumentException{
		if(!Port.isValid(port)){
			throw new IllegalArgumentException("Invalid port number");
		}else if(socketHandlerFactory==null){
			throw new IllegalArgumentException("SocketHandlerFactory is null");
		}
		this.port=port;
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

	/**
	 * Creates a new ServerSocket using the specified port number.
	 */
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
		} catch (final BindException e){
			LOGGER.error("Server could not be started",e);
			interrupt();
			throw new ServerException("The server could not be started");
		} catch (final IOException e) {
			LOGGER.error("Server {} could not be started",this,e);
			interrupt();
			throw new ServerException("The server could not be started");
		}
	}
	
	@Override
	public String toString(){
		return getHost() + ":" + port;
	}
	
	/**
	 * Start a SocketServer using String messages to communicate.
	 * @param args the port number can be added as parameter but this is not mandatory
	 * @throws InterruptedException
	 */
	public static void main(final String[] args) throws InterruptedException{
		int port=-1;
		if(args.length>=1){
			try{
				port=Integer.valueOf(args[0]);
				if(!Port.isValid(port)){
					LOGGER.error("The port number is not a valid number between 0 and 65535");
					return;
				}
			}catch(final NumberFormatException e){
				LOGGER.error("The port is not a number");
				return;
			}
		}
		if(port==-1){
			boolean running=false;
			port=Port.MINIMUM;
			while(!running && port<Port.MAXIMUM){
				try{
					final SocketServer socketServer=new SocketServer(port,new StringClientSocketHandlerFactory());
					socketServer.start();
					Thread.sleep(2000);
					running=true;
				}catch(final ServerException e){
					running=false;
					port++;
				}
			}
		}else{
			try{
				final SocketServer socketServer=new SocketServer(port,new StringClientSocketHandlerFactory());
				socketServer.start();
			}catch(final ServerException e){
				LOGGER.error("The server could not be started on port {}",port);
			}
		}
	}

}
