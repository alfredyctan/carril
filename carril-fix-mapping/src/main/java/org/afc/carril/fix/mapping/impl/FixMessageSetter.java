package org.afc.carril.fix.mapping.impl;

import java.lang.reflect.Method;

import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.util.AccessorMappingRegistry;

public class FixMessageSetter<S, F extends FixMessage> implements Setter<S, F, Object> {

	private String index; 
	
	public FixMessageSetter(String index) {
		this.index = index;
    }
	
	@Override
	public void set(S source, F target, Object value) {
		try {
			if (value == null) {
				throw new IllegalArgumentException("No value set to [" + index + ']');
			}
			Method method = AccessorMappingRegistry.getFixBodyMapping(target, index).getSetMethod();
	        method.invoke(target, value);
		} catch (IllegalArgumentException iae) {
			throw iae;
	    } catch (Exception e) {
	        throw new TransportException("exception on setting index : " + index, e);
	    }
	}
}
