package org.afc.carril.fix.mapping.impl;

import org.afc.carril.fix.mapping.Getter;

import org.afc.carril.transport.TransportException;

public class ClassGetter<S> implements Getter<S> {

	private static final String PACKAGE = "package";
	
	private static final String SIMPLE = "simple";
	
	private static final String NAME = "name";

	private static interface ClassTypeGetter<T> {

		public String get(Class<?> source);

	}

	private ClassTypeGetter<?> getter;
	
	public ClassGetter(String index) {
		switch (index) {
			case NAME:
				getter = (source) -> source.getName();
				break;
			case SIMPLE:
				getter = (source) -> source.getSimpleName();
				break;
			case PACKAGE:
				getter = (source) -> source.getPackage().getName();
				break;
			default:
				throw new TransportException("Unknown class index type [" + index + "], only " + NAME + ", " + SIMPLE + ", " +  PACKAGE + " is supported"); 
		}
	}
	
	@Override
	public Object get(S source) {
		return getter.get(source.getClass());
	}
}
