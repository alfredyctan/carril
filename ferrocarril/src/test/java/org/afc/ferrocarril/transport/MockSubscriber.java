package org.afc.ferrocarril.transport;

import org.afc.ferrocarril.converter.Converter;
import org.afc.ferrocarril.subscriber.AbstractSubscriber;
import org.afc.ferrocarril.subscriber.SubscriberRegistry;
import org.afc.ferrocarril.transport.TransportException;
import org.afc.ferrocarril.transport.TransportListener;

public class MockSubscriber extends AbstractSubscriber {

	public MockSubscriber() {
		this(null, null, null, null, null);
    }
	
	
	protected MockSubscriber(SubscriberRegistry registry, String subject, TransportListener transportListener,
                                Class clazz, Converter converter) {
	    super(registry, subject, transportListener, clazz, converter);
    }

	private int subscribeCount;

	private int unsubscribeCount;

	@Override
	public void subscribe() throws TransportException {
		subscribeCount++;
	}

	@Override
	public void unsubscribe() throws TransportException {
		unsubscribeCount++;
	}

	public int getSubscribeCount() {
    	return subscribeCount;
    }

	public int getUnsubscribeCount() {
    	return unsubscribeCount;
    }

}