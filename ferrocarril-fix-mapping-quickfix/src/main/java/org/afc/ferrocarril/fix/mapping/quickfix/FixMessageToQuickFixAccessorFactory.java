package org.afc.ferrocarril.fix.mapping.quickfix;

import org.afc.ferrocarril.fix.mapping.AccessorFactory;
import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.SessionState;
import org.afc.ferrocarril.fix.mapping.Setter;
import org.afc.ferrocarril.fix.mapping.TagMapper;
import org.afc.ferrocarril.fix.mapping.impl.ConstGetter;
import org.afc.ferrocarril.fix.mapping.impl.FixMessageGetter;
import org.afc.ferrocarril.fix.mapping.impl.SchemaGetter;
import org.afc.ferrocarril.fix.mapping.impl.StateGetter;
import org.afc.ferrocarril.fix.mapping.impl.StateSetter;
import org.afc.ferrocarril.fix.mapping.schema.Reference;
import org.afc.ferrocarril.fix.mapping.schema.Type;
import org.afc.ferrocarril.fix.mapping.schema.Use;
import org.afc.ferrocarril.message.FixMessage;
import org.afc.ferrocarril.transport.TransportException;

import quickfix.Message;

public class FixMessageToQuickFixAccessorFactory implements AccessorFactory<FixMessage, Message, Object> {
	
	@Override
	public Getter<FixMessage> createGetter(SessionState state, Reference reference, String index, Type type) {
		switch (reference) {
			case OBJ:
				return new FixMessageGetter<FixMessage>(index);
			case STATE:
				return new StateGetter<FixMessage>(state, new FixMessageGetter<FixMessage>(index));
			case CONST:
				return new ConstGetter<FixMessage>(index, type);
			case SCHEMA:
				return new SchemaGetter<FixMessage>(index);
			default:
				throw new TransportException("Unsupported reference type " + reference);
		}
	}

	@Override
	public Setter<FixMessage, Message, Object> createSetter(SessionState state, Reference reference, String index, Type type) {
		switch (reference) {
			case Fix:
				return new QuickFixBodySetter(index, type);
			case Fix_HEADER:
				return new QuickFixHeaderSetter(index, type);
			case Fix_TRAILER:
				return new QuickFixTrailerSetter(index, type);
			case STATE:
				return new StateSetter<FixMessage, Message>(state, new FixMessageGetter<FixMessage>(index));
			default:
				throw new TransportException("Unsupported reference type " + reference);
		}
	}
	
	
	@Override
	public TagMapper<FixMessage, Message> createTagMapper(String name, 
		Getter<FixMessage> getter, Setter<FixMessage, Message, Object> setter, String targetIndex, Use use
	) {
	    return new FixMessageToQuickFixTagMapper(name, getter, setter, use);
	}
}
