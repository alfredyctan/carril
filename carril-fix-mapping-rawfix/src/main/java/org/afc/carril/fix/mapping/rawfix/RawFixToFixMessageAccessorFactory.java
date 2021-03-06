package org.afc.carril.fix.mapping.rawfix;

import org.afc.carril.fix.mapping.AccessorFactory;
import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.TagMapper;
import org.afc.carril.fix.mapping.impl.ConstGetter;
import org.afc.carril.fix.mapping.impl.FixMessageSetter;
import org.afc.carril.fix.mapping.impl.SchemaGetter;
import org.afc.carril.fix.mapping.impl.StateGetter;
import org.afc.carril.fix.mapping.impl.StateSetter;
import org.afc.carril.fix.mapping.schema.Reference;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.fix.mapping.schema.Use;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.TransportException;
import org.afc.util.ObjectUtil;

public class RawFixToFixMessageAccessorFactory implements AccessorFactory<RawFix, FixMessage, Object> {
	
	@Override
    public Getter<RawFix> createGetter(SessionState state, Reference reference, String index, Type type) {
    	if (reference == null) {
    		throw new TransportException("Unsupported reference type, only support " + ObjectUtil.arrayToString(Reference.values()));
    	}
    	switch (reference) {
    		case FIX:
    		case FIX_HEADER:
    		case FIX_TRAILER:
    			return new RawFixGetter(index, type);
    		case STATE:
    			return new StateGetter<RawFix>(state, new RawFixGetter(index, type));
    		case CONST:
    			return new ConstGetter<RawFix>(index, type);
    		case SCHEMA:
    			return new SchemaGetter<RawFix>(index);
    		default:
    			throw new TransportException("Unsupported reference type " + reference + ", only support " + ObjectUtil.arrayToString(Reference.values()));
    	}
    }

	@Override
	public Setter<RawFix, FixMessage, Object> createSetter(SessionState state, Reference reference, String index, Type type) {
    	if (reference == null) {
    		throw new TransportException("Unsupported reference type, only support " + ObjectUtil.arrayToString(Reference.values()));
    	}
		switch (reference) {
			case OBJ:
				return new FixMessageSetter<RawFix, FixMessage>(index);
			case STATE:
				return new StateSetter<RawFix, FixMessage>(state, new RawFixGetter(index, type));
			default:
				throw new TransportException("Unsupported reference type " + reference + 
					", only support " + ObjectUtil.arrayToString(Reference.values()));
		}
	}
	
	@Override
	public TagMapper<RawFix, FixMessage> createTagMapper(String name, Getter<RawFix> getter, Setter<RawFix, FixMessage, Object> setter, String targetIndex, Use use) {
	    return new RawFixToFixMessageTagMapper(name, getter, setter, targetIndex, use);
	}
}
