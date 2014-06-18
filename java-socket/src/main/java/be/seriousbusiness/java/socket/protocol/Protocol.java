package be.seriousbusiness.java.socket.protocol;

public interface Protocol<M> {
	M begin();
	
	State serverRespond(final M message);
	M respondServer(final M message);
	
	State clientRespond(final M message);
	M respondClient(final M message);
}
