package org.afc.ferrocarril.converter;

import org.afc.ferrocarril.message.GenericMessage;
import org.afc.ferrocarril.transport.TransportException;

public interface Converter<W, G extends GenericMessage> {

	public G parse(W wireFormat, Class<? extends G> clazz) throws TransportException;

	public W format(G message) throws TransportException;

}
