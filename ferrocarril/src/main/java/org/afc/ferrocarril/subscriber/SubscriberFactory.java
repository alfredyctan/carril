package org.afc.ferrocarril.subscriber;

import org.afc.ferrocarril.converter.Converter;
import org.afc.ferrocarril.message.GenericMessage;
import org.afc.ferrocarril.transport.TransportListener;

public interface SubscriberFactory {

	public Subscriber createSubscriber(String subject, TransportListener listener, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter);
	
}
