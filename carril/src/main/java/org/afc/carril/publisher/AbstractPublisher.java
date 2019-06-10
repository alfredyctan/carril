package org.afc.carril.publisher;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.SubjectRegistry;

public abstract class AbstractPublisher<C extends SubjectContext> implements Publisher {

	protected SubjectRegistry<C> registry;

	protected String subject;

	protected Class<? extends GenericMessage> clazz;

	protected Converter converter;

	protected <W, G extends GenericMessage> AbstractPublisher(SubjectRegistry<C> registry, String subject, Class<G> clazz, Converter<W, G> converter) {
		this.registry = registry;
        this.subject = subject;
        this.clazz = clazz;
        this.converter = converter;
    }
}