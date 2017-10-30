package org.afc.carril.transport;

public interface ExceptionListener {

	public <E extends TransportException> void onException(E e);
	
}
