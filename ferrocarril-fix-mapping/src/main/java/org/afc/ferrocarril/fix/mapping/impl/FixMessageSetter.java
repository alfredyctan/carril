package org.afc.ferrocarril.fix.mapping.impl;

import java.lang.reflect.Method;

import org.afc.ferrocarril.fix.mapping.Setter;
import org.afc.ferrocarril.message.FixMessage;
import org.afc.ferrocarril.transport.TransportException;

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
			Method method = target.getFixMessageMap().get(index).getSetMethod();
	        method.invoke(target, value);
		} catch (IllegalArgumentException iae) {
			throw iae;
	    } catch (Exception e) {
	        throw new TransportException(e);
	    }
	}
}
