package org.afc.ferrocarril.fix.mapping.quickfix;

import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.schema.Type;

import quickfix.FieldMap;
import quickfix.Message;

public class QuickFixHeaderGetter implements Getter<Message> {

	private Getter<FieldMap> getter;
	
	public QuickFixHeaderGetter(String index, Type type) {
		getter = new QuickFixFieldGetter(index, type);
    }

	@Override
	public Object get(Message source) {
	    return getter.get(source.getHeader());
	}
}
