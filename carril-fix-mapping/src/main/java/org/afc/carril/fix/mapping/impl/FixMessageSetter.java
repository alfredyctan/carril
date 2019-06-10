package org.afc.carril.fix.mapping.impl;

import java.lang.reflect.Method;

import org.afc.carril.fix.mapping.Setter;

import org.afc.carril.annotation.Carril.Wire;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.util.AccessorMappingRegistry;

public class FixMessageSetter<S> implements Setter<S, Object, Object> {

	private String index; 
	
	public FixMessageSetter(String index) {
		this.index = index;
    }
	
	@Override
	public void set(S source, Object target, Object value) {
		try {
			if (value == null) {
				throw new IllegalArgumentException("No value set to [" + index + ']');
			}
			Method method = AccessorMappingRegistry.getMapping(Wire.Fix, target, index).getSetMethod();
	        method.invoke(target, value);
		} catch (IllegalArgumentException iae) {
			throw iae;
	    } catch (Exception e) {
	        throw new TransportException("exception on setting index : " + index, e);
	    }
	}
}
