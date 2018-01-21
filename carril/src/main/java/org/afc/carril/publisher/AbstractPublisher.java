package org.afc.carril.publisher;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportListener;

public abstract class AbstractPublisher<C extends SubjectContext> implements Publisher {

	protected SubjectRegistry<C> registry;

	protected String subject;

	protected Class<? extends GenericMessage> clazz;

	protected Converter<Object, GenericMessage> converter;

	protected AbstractPublisher(SubjectRegistry<C> registry, String subject, TransportListener transportListener, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter) {
		this.registry = registry;
        this.subject = subject;
        this.clazz = clazz;
        this.converter = converter;
    }
}