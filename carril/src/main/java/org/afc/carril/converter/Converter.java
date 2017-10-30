package org.afc.carril.converter;

import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.TransportException;

public interface Converter<W, G extends GenericMessage> {

	public G parse(W wireFormat, Class<? extends G> clazz) throws TransportException;

	public W format(G message) throws TransportException;

}
