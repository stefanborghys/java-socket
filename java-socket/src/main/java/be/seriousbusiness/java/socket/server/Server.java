package be.seriousbusiness.java.socket.server;

public interface Server extends Runnable {
	int getPort();
	String getHost();
}
