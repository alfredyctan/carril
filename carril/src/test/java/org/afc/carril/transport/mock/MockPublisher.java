package org.afc.carril.transport.mock;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.publisher.AbstractPublisher;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;

@SuppressWarnings("rawtypes")
public class MockPublisher extends AbstractPublisher {

	public MockPublisher(SubjectRegistry registry, String subject) {
		this(null, null, null, null, null);
    }
	
	@SuppressWarnings("unchecked")
	protected MockPublisher(SubjectRegistry registry, String subject, TransportListener transportListener, Class clazz, Converter converter) {
	    super(registry, subject, transportListener, clazz, converter);
    }

	private int publishCount;

	private int requestCount;

	public int getPublishCount() {
    	return publishCount;
    }

	public int getRequestCount() {
    	return requestCount;
    }

	@Override
	public void publish(String subject, GenericMessage message, Converter<Object, GenericMessage> converter) throws TransportException {
		publishCount++;
	}


	@Override
	public <G extends GenericMessage> G publishRequest(String subject, GenericMessage message, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter, int timeout) throws TransportException {
		requestCount++;
		return (G)new MockMessage();
	}

}
