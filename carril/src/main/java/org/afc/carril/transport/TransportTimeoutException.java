package org.afc.carril.transport;

public class TransportTimeoutException extends TransportException {

	private static final long serialVersionUID = 6267069692261267561L;

	public TransportTimeoutException(String message) {
		super(message);
	}

	public TransportTimeoutException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public TransportTimeoutException(Throwable throwable) {
		super(throwable);
	}
}
