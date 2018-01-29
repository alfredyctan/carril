package org.afc.carril.publisher;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.TransportException;

public interface Publisher {

	public <W, G extends GenericMessage> void publish(String subject, G message, Converter<W, G> converter) throws TransportException;

	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<? extends G> clazz, Converter<W, G> converter, int timeout) throws TransportException;

	public void dispose();
}
