package be.seriousbusiness.java.socket.handler.factory;

import java.net.Socket;

import be.seriousbusiness.java.socket.handler.Handler;

public interface SocketHandlerFactory<H extends Handler> {
	H create(final Socket socket);
}
