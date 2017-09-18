package org.afc.ferrocarril.transport;

public interface ExceptionListener<E extends TransportException> {

	public void onException(E e);
	
}
