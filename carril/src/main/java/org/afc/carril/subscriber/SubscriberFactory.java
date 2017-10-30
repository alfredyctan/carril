package org.afc.carril.subscriber;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.TransportListener;

public interface SubscriberFactory {

	public Subscriber createSubscriber(String subject, TransportListener listener, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter);
	
}
