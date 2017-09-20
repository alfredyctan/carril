package org.afc.ferrocarril.fix.mapping.quickfix;

import org.afc.ferrocarril.fix.mapping.Setter;
import org.afc.ferrocarril.fix.mapping.schema.Type;
import org.afc.ferrocarril.message.FixMessage;

import quickfix.FieldMap;
import quickfix.Message;

public class QuickFixHeaderSetter implements Setter<FixMessage, Message, Object> {
	
	private Setter<FixMessage, FieldMap, Object> setter;
	
	public QuickFixHeaderSetter(String index, Type type) {
		setter = new QuickFixFieldSetter(index, type);
    }
	
	@Override
	public void set(FixMessage source, Message target, Object value) {
		setter.set(source, target.getHeader(), value);
	}
}
