package be.seriousbusiness.java.socket.server;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import be.seriousbusiness.java.socket.handler.factory.client.StringClientSocketHandlerFactory;
import be.seriousbusiness.java.socket.server.SocketServer;

public class BaseServerTest {
	
	@Rule
	public ExpectedException expectedException=ExpectedException.none();
	
	@Test
	public void testConstructor(){
		final SocketServer baseServer=new SocketServer(3333,new StringClientSocketHandlerFactory());
		baseServer.start();
		baseServer.interrupt();
	}
	
	@Test
	public void testConstructorNegativePortNumber(){
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Invalid port number");
		new SocketServer(-1,new StringClientSocketHandlerFactory());
	}
	
	@Test
	public void testConstructorIllegalPortNumber(){
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Invalid port number");
		new SocketServer(65536,new StringClientSocketHandlerFactory());
	}
	
	@Test
	public void testConstructorNullClientHandlerFactory(){
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("SocketHandlerFactory is null");
		new SocketServer(1234,null);
	}

}
