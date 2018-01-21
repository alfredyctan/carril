package org.afc.carril.publisher;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.TransportException;

public interface Publisher {

	public void publish(String subject, GenericMessage message, Converter<Object, GenericMessage> converter) throws TransportException;

	public <G extends GenericMessage> G publishRequest(String subject, GenericMessage message, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter, int timeout) throws TransportException;
	
}
