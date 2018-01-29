package org.afc.carril.subscriber;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.TransportListener;

public interface SubscriberFactory {

	public <W, G extends GenericMessage> Subscriber createSubscriber(String subject, TransportListener listener, Class<G> clazz, Converter<W, G> converter);
	
}
