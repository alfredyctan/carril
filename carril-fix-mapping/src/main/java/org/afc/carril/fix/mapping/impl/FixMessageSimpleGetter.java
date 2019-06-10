package org.afc.carril.fix.mapping.impl;

import java.lang.reflect.Method;

import org.afc.carril.fix.mapping.Getter;

import org.afc.carril.annotation.Carril.Wire;
import org.afc.carril.transport.AccessorMapping;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.util.AccessorMappingRegistry;

public class FixMessageSimpleGetter<F> implements Getter<F> {

	private String index;
	
	public FixMessageSimpleGetter(String index) {
		this.index = index;
    }
		
	@Override
	public Object get(F source) {
		try {
			AccessorMapping mapping = AccessorMappingRegistry.getMapping(Wire.Fix, source, index);
			Method method = mapping.getGetMethod();
			Object object = method.invoke(source);
			return object;
        } catch (Exception e) {
	        throw new TransportException("Error on getter value for " + index, e);
        }
	}
}
