package org.afc.carril.transport;

public class FieldNotFoundException extends TransportException {

	private static final long serialVersionUID = 1481265681999812382L;

	public FieldNotFoundException(String message) {
		super(message);
	}

	public FieldNotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

	public FieldNotFoundException(Throwable throwable) {
		super(throwable);
	}
}
