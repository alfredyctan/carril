package org.afc.ferrocarril.converter;

import org.afc.ferrocarril.message.GenericMessage;

public interface ConverterFactory<A> {

	public Converter<Object, GenericMessage> createConverter(A arg);

}
