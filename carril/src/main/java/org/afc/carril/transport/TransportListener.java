package org.afc.carril.transport;

import java.util.concurrent.ExecutorService;

import org.afc.carril.message.GenericMessage;

public interface TransportListener {

	public GenericMessage onMessage(GenericMessage message);

	public default ExecutorService getExecutorService() {
		return null;
	};
	
}
