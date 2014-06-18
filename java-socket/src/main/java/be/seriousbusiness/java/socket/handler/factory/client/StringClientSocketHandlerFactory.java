package be.seriousbusiness.java.socket.handler.factory.client;

import java.net.Socket;

import be.seriousbusiness.java.socket.handler.client.StringClientSocketHandler;
import be.seriousbusiness.java.socket.handler.factory.SocketHandlerFactory;
import be.seriousbusiness.java.socket.protocol.StringProtocol;

public class StringClientSocketHandlerFactory implements SocketHandlerFactory<StringClientSocketHandler> {

	@Override
	public StringClientSocketHandler create(final Socket socket) {
		return new StringClientSocketHandler(socket,new StringProtocol());
	}

}
