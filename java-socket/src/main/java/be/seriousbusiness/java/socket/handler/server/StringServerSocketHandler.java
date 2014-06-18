package be.seriousbusiness.java.socket.handler.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import be.seriousbusiness.java.socket.handler.AbstractBaseHandler;
import be.seriousbusiness.java.socket.protocol.Protocol;

public class StringServerSocketHandler extends AbstractBaseHandler<String> {
private static final Logger LOGGER=LoggerFactory.getLogger(StringServerSocketHandler.class);
	
	public StringServerSocketHandler(final Socket socket,final Protocol<String> protocol) throws IllegalArgumentException{
		super(socket,protocol);
	}

	@Override
	public void run() {
		try {
			final PrintWriter printWriter=new PrintWriter(socket.getOutputStream(), true);
			final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String message;
			while((message=bufferedReader.readLine())!=null){
				switch(protocol.serverRespond(message)){
				case MESSAGE : 
					LOGGER.debug("{} says {}",this,message);
					printWriter.println(protocol.respondServer(message));
					break;
				case STOP: 
					LOGGER.debug("{} says {}",this,message);
					LOGGER.debug("Bye {}",this,message);
					socket.close();
					return;
				}
			}
			socket.close();
		} catch (final IOException e) {
			LOGGER.error("Socket {} could not be handled",this,e);
		}
	}
}
