package org.afc.carril.transport;

public class TransportException extends RuntimeException {

	private static final long serialVersionUID = -3258915140586258115L;

	public TransportException(String message) {
		super(message);
	}

	public TransportException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public TransportException(Throwable throwable) {
		super(throwable);
	}
}
