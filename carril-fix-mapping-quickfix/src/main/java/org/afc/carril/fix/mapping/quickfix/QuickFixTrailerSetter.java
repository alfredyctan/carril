package org.afc.carril.fix.mapping.quickfix;

import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.message.FixMessage;

import quickfix.FieldMap;
import quickfix.Message;

public class QuickFixTrailerSetter implements Setter<FixMessage, Message, Object> {
	
	private Setter<FixMessage, FieldMap, Object> setter;
	
	public QuickFixTrailerSetter(String index, Type type) {
		setter = new QuickFixFieldSetter(index, type);
    }
	
	@Override
	public void set(FixMessage source, Message target, Object value) {
		setter.set(source, target.getTrailer(), value);
	}
}