package org.afc.carril.fix.mapping.impl;

import java.lang.reflect.Field;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.schema.FixConv;
import org.afc.carril.fix.mapping.schema.Type;

import org.afc.carril.transport.TransportException;

public class ConstGetter<S> implements Getter<S> {

	private static Object constant; 

	public ConstGetter(FixConv fixConv, String index, Type type) {
		int lastDot = index.lastIndexOf('.');
		String className = index.substring(0, lastDot);
		String constName = index.substring(lastDot + 1);
		
		try {
	        Class<?> clazz = lookupClass(fixConv.getPackagePrefix(), className);
	        Field field = clazz.getField(constName);
	        
	        constant = field.get(null);
	        if (constant == null) {
	        	throw new TransportException("Unable to find constant " + index);
	        }
        } catch (TransportException e) {
	        throw e;
        } catch (Exception e) {
        	throw new TransportException("Error on finding constant " + index, e);
        }
	}

	private static Class<?> lookupClass(String prefix, String className) throws ClassNotFoundException {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return Class.forName(prefix + '.'+ className);
		}
	}

	@Override
	public Object get(S source) {
		return constant;
	}
}
