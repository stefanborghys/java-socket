package be.seriousbusiness.java.socket.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple Protocol implementation using String messages to communicate between Server and Client.
 * @author seriousbusiness
 * @author Stefan Borghys
 * 
 */
public class StringProtocol implements Protocol<String> {
	private static final Logger LOGGER=LoggerFactory.getLogger(StringProtocol.class);
	private static final String HELLO="HELLO",
			STOP="STOP";

	@Override
	public String begin() {
		return HELLO;
	}

	@Override
	public State serverRespond(final String message) {
		if(message==null || message.startsWith(STOP)){
			return State.STOP;
		}
		return State.MESSAGE;
	}

	@Override
	public String respondServer(final String message) {
		try {
			Thread.sleep(5000);
		} catch (final InterruptedException e) {
			LOGGER.error("Protocol is interrupted",e);
		}
		return String.valueOf(System.currentTimeMillis());
	}

	@Override
	public State clientRespond(final String message) {
		if(message==null || message.startsWith(STOP)){
			return State.STOP;
		}
		return State.MESSAGE;
	}

	@Override
	public String respondClient(final String message) {
		try {
			Thread.sleep(10000);
		} catch (final InterruptedException e) {
			LOGGER.error("Protocol is interrupted",e);
		}
		return "Go on...";
	}

}
