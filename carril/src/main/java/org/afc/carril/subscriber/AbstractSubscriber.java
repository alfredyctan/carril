package org.afc.carril.subscriber;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;

public abstract class AbstractSubscriber<C extends SubjectContext> implements Subscriber {

	protected SubjectRegistry<C> registry;

	protected String subject;

	protected TransportListener listener;

	protected Class<? extends GenericMessage> clazz;

	protected Converter converter;

	protected <W, G extends GenericMessage> AbstractSubscriber(SubjectRegistry<C> registry, String subject, TransportListener listener, Class<G> clazz, Converter<W, G> converter) {
		this.registry = registry;
        this.subject = subject;
        this.listener = listener;
        this.clazz = clazz;
        this.converter = converter;
    }

	@Override
    public void subscribe() throws TransportException {
       	registry.getSubjectContext(subject).addSubscriber(this);
    }

	@Override
    public void unsubscribe() throws TransportException {
		registry.getSubjectContext(subject).removeSubscriber(this);
	}
}