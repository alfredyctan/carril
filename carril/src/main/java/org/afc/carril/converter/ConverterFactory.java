package org.afc.carril.converter;

import org.afc.carril.message.GenericMessage;

public interface ConverterFactory<A> {

	public Converter<Object, GenericMessage> createConverter(A arg);

}
