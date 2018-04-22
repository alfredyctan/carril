package org.afc.carril.transport;

import java.util.concurrent.ExecutorService;

import org.afc.carril.message.GenericMessage;

public interface TransportListener<G extends GenericMessage> {

	public G onMessage(G message);

	public default ExecutorService getExecutorService() {
		return null;
	};
	
}
