package org.afc.carril.transport.mock;

import org.afc.carril.converter.Converter;
import org.afc.carril.subscriber.AbstractSubscriber;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;

@SuppressWarnings("rawtypes")
public class MockSubscriber extends AbstractSubscriber {

	public MockSubscriber() {
		this(null, null, null, null, null);
    }
	
	
	@SuppressWarnings("unchecked")
	protected MockSubscriber(SubjectRegistry registry, String subject, TransportListener transportListener,
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
