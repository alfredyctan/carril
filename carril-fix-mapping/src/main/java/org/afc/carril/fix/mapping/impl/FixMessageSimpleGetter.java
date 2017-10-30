package org.afc.carril.fix.mapping.impl;

import java.lang.reflect.Method;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.AccessorMapping;
import org.afc.carril.transport.TransportException;

public class FixMessageSimpleGetter<F extends FixMessage> implements Getter<F> {

	private String index;
	
	public FixMessageSimpleGetter(String index) {
		this.index = index;
    }
		
	@Override
	public Object get(F source) {
		try {
			AccessorMapping mapping = source.getFixMessageMap().get(index);
			Method method = mapping.getGetMethod();
			Object object = method.invoke(source);
			return object;
        } catch (Exception e) {
	        throw new TransportException("Error on getter value for " + index, e);
        }
	}
}
