package org.afc.carril.subscriber;

import org.afc.carril.transport.TransportException;

public interface Subscriber {

	public void subscribe() throws TransportException;

	public void unsubscribe() throws TransportException;

}
