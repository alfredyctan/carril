package org.afc.ferrocarril.fix.mapping.quickfix;

import org.afc.ferrocarril.fix.mapping.AccessorFactory;
import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.SessionState;
import org.afc.ferrocarril.fix.mapping.Setter;
import org.afc.ferrocarril.fix.mapping.TagMapper;
import org.afc.ferrocarril.fix.mapping.impl.ConstGetter;
import org.afc.ferrocarril.fix.mapping.impl.FixMessageSetter;
import org.afc.ferrocarril.fix.mapping.impl.SchemaGetter;
import org.afc.ferrocarril.fix.mapping.impl.StateGetter;
import org.afc.ferrocarril.fix.mapping.impl.StateSetter;
import org.afc.ferrocarril.fix.mapping.schema.Reference;
import org.afc.ferrocarril.fix.mapping.schema.Type;
import org.afc.ferrocarril.fix.mapping.schema.Use;
import org.afc.ferrocarril.message.QuickFixMessage;
import org.afc.ferrocarril.transport.TransportException;

import quickfix.Message;

public class QuickFixToFixMessageAccessorFactory implements AccessorFactory<Message, QuickFixMessage, Object> {
	
	@Override
    public Getter<Message> createGetter(SessionState state, Reference reference, String index, Type type) {
    	if (reference == null) {
    		throw new TransportException("Unsupported reference type, only support {" + Reference.Fix + "," + Reference.STATE + "," + Reference.CONST + "," + Reference.SCHEMA + "}");
    	}
    	switch (reference) {
    		case Fix:
    			return new QuickFixBodyGetter(index, type);
    		case Fix_HEADER:
    			return new QuickFixHeaderGetter(index, type);
    		case Fix_TRAILER:
    			return new QuickFixTrailerGetter(index, type);
    		case STATE:
    			return new StateGetter<Message>(state, new QuickFixBodyGetter(index, type));
    		case CONST:
    			return new ConstGetter<Message>(index, type);
    		case SCHEMA:
    			return new SchemaGetter<Message>(index);
    		default:
    			throw new TransportException("Unsupported reference type " + reference + 
    				", only support {" + Reference.Fix + "," + Reference.STATE + "," + Reference.CONST + "," + Reference.SCHEMA + "}");
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
