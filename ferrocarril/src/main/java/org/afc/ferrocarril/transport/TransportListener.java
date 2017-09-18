package org.afc.ferrocarril.transport;

import java.util.concurrent.ExecutorService;

import org.afc.ferrocarril.message.GenericMessage;

public interface TransportListener {

	public GenericMessage onMessage(GenericMessage message);

	public ExecutorService getExecutorService();
	
}
