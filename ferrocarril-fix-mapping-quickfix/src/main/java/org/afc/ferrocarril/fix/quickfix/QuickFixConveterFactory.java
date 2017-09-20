package org.afc.ferrocarril.fix.quickfix;

import org.afc.ferrocarril.converter.Converter;
import org.afc.ferrocarril.converter.ConverterFactory;
import org.afc.ferrocarril.message.GenericMessage;

public class QuickFixConveterFactory implements ConverterFactory<String> {

	@SuppressWarnings("unchecked")
    @Override
    public Converter<Object, GenericMessage> createConverter(String path) {
	    return (Converter)new SchemaBaseQuickFixConverter(path);
    }

}
