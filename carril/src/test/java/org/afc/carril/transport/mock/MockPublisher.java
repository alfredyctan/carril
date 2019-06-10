package org.afc.carril.transport.mock;

import org.afc.carril.converter.Converter;
import org.afc.carril.publisher.AbstractPublisher;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportException;

@SuppressWarnings("rawtypes")
public class MockPublisher extends AbstractPublisher {

	public MockPublisher(SubjectRegistry registry, String subject) {
		this(null, null, null, null);
    }
	
	@SuppressWarnings("unchecked")
	protected MockPublisher(SubjectRegistry registry, String subject, Class clazz, Converter converter) {
	    super(registry, subject, clazz, converter);
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
	public <W, G extends org.afc.carril.message.GenericMessage> void publish(String subject, G message,
			Converter<W, G> converter) throws TransportException {
		publishCount++;
	}
	
	@Override
	public <W, G extends org.afc.carril.message.GenericMessage> G publishRequest(String subject, G message,
			Class<? extends G> clazz, Converter<W, G> converter, int timeout) throws TransportException {
		requestCount++;
		return (G)new MockMessage();
	}
	
	@Override
	public void dispose() {
		
	}
}
