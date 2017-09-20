package org.afc.ferrocarril.transport;

public interface ExceptionListener {

	public <E extends TransportException> void onException(E e);
	
}
