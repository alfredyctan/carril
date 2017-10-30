package org.afc.carril.fix.mapping.quickfix;

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
import org.afc.carril.message.QuickFixMessage;
import org.afc.carril.transport.TransportException;

import quickfix.Message;

public class QuickFixToFixMessageAccessorFactory implements AccessorFactory<Message, QuickFixMessage, Object> {
	
	@Override
    public Getter<Message> createGetter(SessionState state, Reference reference, String index, Type type) {
    	if (reference == null) {
    		throw new TransportException("Unsupported reference type, only support {" + Reference.FIX + "," + Reference.STATE + "," + Reference.CONST + "," + Reference.SCHEMA + "}");
    	}
    	switch (reference) {
    		case FIX:
    			return new QuickFixBodyGetter(index, type);
    		case FIX_HEADER:
    			return new QuickFixHeaderGetter(index, type);
    		case FIX_TRAILER:
    			return new QuickFixTrailerGetter(index, type);
    		case STATE:
    			return new StateGetter<Message>(state, new QuickFixBodyGetter(index, type));
    		case CONST:
    			return new ConstGetter<Message>(index, type);
    		case SCHEMA:
    			return new SchemaGetter<Message>(index);
    		default:
    			throw new TransportException("Unsupported reference type " + reference + 
    				", only support {" + Reference.FIX + "," + Reference.STATE + "," + Reference.CONST + "," + Reference.SCHEMA + "}");
    	}
    }

	@Override
	public Setter<Message, QuickFixMessage, Object> createSetter(SessionState state, Reference reference, String index, Type type) {
		if (reference == null) {
			throw new TransportException("Unsupported reference type, only support {" + Reference.OBJ + "," + Reference.STATE + "}");
		}
		switch (reference) {
			case OBJ:
				return new FixMessageSetter<Message, QuickFixMessage>(index);
			case STATE:
				return new StateSetter<Message, QuickFixMessage>(state, new QuickFixBodyGetter(index, type));
			default:
				throw new TransportException("Unsupported reference type " + reference + 
					", only support {" + Reference.OBJ + "," + Reference.STATE + "}");
		}
	}
	
	@Override
	public TagMapper<Message, QuickFixMessage> createTagMapper(String name, Getter<Message> getter, Setter<Message, QuickFixMessage, Object> setter, String targetIndex, Use use) {
	    return new QuickFixToFixMessageTagMapper(name, getter, setter, targetIndex, use);
	}
}
