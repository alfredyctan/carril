package org.afc.carril.subscriber;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.TransportListener;

public abstract class AbstractSubscriber<C extends SubjectContext> implements Subscriber {

	protected SubscriberRegistry<C> registry;

	protected String subject;

	protected TransportListener transportListener;

	protected Class<? extends GenericMessage> clazz;

	protected Converter<Object, GenericMessage> converter;

	protected AbstractSubscriber(SubscriberRegistry<C> registry, String subject, TransportListener transportListener, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter) {
		this.registry = registry;
        this.subject = subject;
        this.transportListener = transportListener;
        this.clazz = clazz;
        this.converter = converter;
    }
}