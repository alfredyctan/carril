package org.afc.carril.fix.mapping.quickfix;

import java.util.List;

import org.afc.carril.fix.mapping.AccessorFactory;
import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.TagMapper;
import org.afc.carril.fix.mapping.impl.ClassGetter;
import org.afc.carril.fix.mapping.impl.ConstGetter;
import org.afc.carril.fix.mapping.impl.FixMessageGetter;
import org.afc.carril.fix.mapping.impl.SchemaGetter;
import org.afc.carril.fix.mapping.impl.StateGetter;
import org.afc.carril.fix.mapping.impl.StateSetter;
import org.afc.carril.fix.mapping.schema.FixConv;
import org.afc.carril.fix.mapping.schema.Reference;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.fix.mapping.schema.Use;
import org.afc.carril.transport.TransportException;

import quickfix.Message;

public class FixMessageToQuickFixAccessorFactory implements AccessorFactory<Object, Message, Object> {
	
	@Override
	public Getter<Object> createGetter(FixConv fixConv, SessionState state, Reference reference, String index, Type type) {
		switch (reference) {
			case OBJ:
				return new FixMessageGetter<Object>(index);
			case CLASS:
				return new ClassGetter<Object>(index);
			case STATE:
				return new StateGetter<Object>(state, new FixMessageGetter<Object>(index));
			case CONST:
				return new ConstGetter<Object>(fixConv, index, type);
			case SCHEMA:
				return new SchemaGetter<Object>(index, type);
			default:
				throw new TransportException("Unsupported reference type " + reference);
		}
	}

	@Override
	public Setter<Object, Message, Object> createSetter(FixConv fixConv, SessionState state, Reference reference, String index, Type type, List<String> order) {
		switch (reference) {
			case FIX:
				return new QuickFixBodySetter(index, type, order);
			case FIX_HEADER:
				return new QuickFixHeaderSetter(index, type, order);
			case FIX_TRAILER:
				return new QuickFixTrailerSetter(index, type, order);
			case STATE:
				return new StateSetter<Object, Message>(state, new FixMessageGetter<Object>(index));
			default:
				throw new TransportException("Unsupported reference type " + reference);
		}
	}
	
	
	@Override
	public TagMapper<Object, Message> createTagMapper(String name, 
		Getter<Object> getter, Setter<Object, Message, Object> setter, String targetIndex, Use use
	) {
	    return new FixMessageToQuickFixTagMapper(name, getter, setter, use);
	}
}
