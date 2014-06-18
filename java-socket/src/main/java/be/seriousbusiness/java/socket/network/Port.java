package be.seriousbusiness.java.socket.network;

public final class Port {
	public static final int MINIMUM=0,MAXIMUM=65535;
	
	public static final boolean isValid(final int port){
		return port>=MINIMUM && port<=MAXIMUM;
	}

}
