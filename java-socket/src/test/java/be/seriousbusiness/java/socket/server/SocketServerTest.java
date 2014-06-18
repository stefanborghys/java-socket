package be.seriousbusiness.java.socket.server;


import org.hamcrest.CoreMatchers;
import org.hamcrest.text.IsEmptyString;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import be.seriousbusiness.java.socket.handler.factory.client.StringClientSocketHandlerFactory;

/**
 * Simple SocketServer test.
 * @author seriousbusiness
 * @author Stefan Borghys
 */
public class SocketServerTest {
	
	@Rule
	public ExpectedException expectedException=ExpectedException.none();
	
	@Rule
	public ErrorCollector errorCollector=new ErrorCollector();
	
	/**
	 * Tests the creation of a new SocketServer, starts it and interrupts it.
	 * @throws InterruptedException 
	 */
	@Test
	public void testConstructor() throws InterruptedException{
		final SocketServer socketServer=new SocketServer(3333,new StringClientSocketHandlerFactory());
		Assert.assertEquals("The port number should be equal to the one set during construction",3333,socketServer.getPort());
		Assert.assertEquals("The host should be empty when the server is not yet started","",socketServer.getHost());
		socketServer.start();
		Thread.sleep(5000); // Wait until the SocketServer is started!
		Assert.assertEquals("The port number should be equal to the one set during construction",3333,socketServer.getPort());
		errorCollector.checkThat("The host should be set when the server is started",socketServer.getHost(),CoreMatchers.not(IsEmptyString.isEmptyString()));
		socketServer.interrupt();
	}
	
	/**
	 * Tests the constructor by adding a negative illegal port number.
	 */
	@Test
	public void testConstructorNegativePortNumber(){
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Invalid port number");
		new SocketServer(-1,new StringClientSocketHandlerFactory());
	}
	
	/**
	 * Tests the constructor by adding an illegal to large port number.
	 */
	@Test
	public void testConstructorIllegalPortNumber(){
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("Invalid port number");
		new SocketServer(65536,new StringClientSocketHandlerFactory());
	}
	
	/**
	 * Tests the constructor by adding an illegal <code>null</code> host.
	 */
	@Test
	public void testConstructorNullClientHandlerFactory(){
		expectedException.expect(IllegalArgumentException.class);
		expectedException.expectMessage("SocketHandlerFactory is null");
		new SocketServer(1234,null);
	}

}
