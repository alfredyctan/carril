package org.afc.ferrocarril.subscriber;

import org.afc.ferrocarril.converter.Converter;
import org.afc.ferrocarril.message.GenericMessage;
import org.afc.ferrocarril.transport.SubjectContext;
import org.afc.ferrocarril.transport.TransportListener;

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