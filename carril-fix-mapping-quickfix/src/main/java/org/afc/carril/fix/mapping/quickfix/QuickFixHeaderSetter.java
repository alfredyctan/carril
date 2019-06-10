package org.afc.carril.fix.mapping.quickfix;

import java.util.List;

import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.schema.Type;

import quickfix.FieldMap;
import quickfix.Message;

public class QuickFixHeaderSetter implements Setter<Object, Message, Object> {
	
	private Setter<Object, FieldMap, Object> setter;
	
	public QuickFixHeaderSetter(String index, Type type, List<String> order) {
		setter = new QuickFixFieldSetter(index, type, order);
    }
	
	@Override
	public void set(Object source, Message target, Object value) {
		setter.set(source, target.getHeader(), value);
	}
}
