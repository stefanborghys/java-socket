package be.seriousbusiness.java.socket.protocol;

/**
 * Describes the state a discussion between client and server is in.</br>
 * - MESSAGE: a message can freely be transferred</br>
 * - STOP: the discussion comes to an end
 * @author seriousbusiness
 * @author Stefan Borghys
 *
 */
public enum State {
	MESSAGE,
	STOP;
}
