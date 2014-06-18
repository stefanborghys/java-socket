package be.seriousbusiness.java.socket.handler.factory;

import java.net.Socket;

import be.seriousbusiness.java.socket.handler.Handler;

/**
 * Responsible for the creation of Handlers using a socket.
 * @author seriousbusiness
 * @author Stefan Borghys
 * 
 * @param <H> the type of Handler
 */
public interface SocketHandlerFactory<H extends Handler> {
	/**
	 * Create a new Handler using a specified Socket.
	 * @param socket
	 * @return
	 */
	H create(final Socket socket);
}
