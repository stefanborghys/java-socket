package be.seriousbusiness.java.socket.client;

public interface Client extends Runnable {
	int getPort();
	String getHost();
}
