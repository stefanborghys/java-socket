package be.seriousbusiness.java.socket.handler.factory.server;

import java.net.Socket;

import be.seriousbusiness.java.socket.handler.factory.SocketHandlerFactory;
import be.seriousbusiness.java.socket.handler.server.StringServerSocketHandler;
import be.seriousbusiness.java.socket.protocol.StringProtocol;

public class StringServerSocketHandlerFactory implements SocketHandlerFactory<StringServerSocketHandler> {
	
	@Override
	public StringServerSocketHandler create(final Socket socket) {
		return new StringServerSocketHandler(socket,new StringProtocol());
	}
	
}
