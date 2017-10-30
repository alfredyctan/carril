package org.afc.carril.fix.quickfix;

import org.afc.carril.converter.Converter;
import org.afc.carril.converter.ConverterFactory;
import org.afc.carril.message.GenericMessage;

public class QuickFixConveterFactory implements ConverterFactory<String> {

	@SuppressWarnings("unchecked")
    @Override
    public Converter<Object, GenericMessage> createConverter(String path) {
	    return (Converter)new SchemaBaseQuickFixConverter(path);
    }

}
