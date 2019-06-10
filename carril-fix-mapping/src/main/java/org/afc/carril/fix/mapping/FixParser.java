package org.afc.carril.fix.mapping;

public interface FixParser<S, T> {

	public T parse(S source, Class<T> clazz);
}
