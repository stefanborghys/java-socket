package be.seriousbusiness.java.socket.handler;


public interface Handler extends Runnable {
	int getClientPort();
	String getClientHost();
}
