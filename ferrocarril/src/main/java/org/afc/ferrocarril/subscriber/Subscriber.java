package org.afc.ferrocarril.subscriber;

import org.afc.ferrocarril.transport.TransportException;

public interface Subscriber {

	public void subscribe() throws TransportException;

	public void unsubscribe() throws TransportException;

}
