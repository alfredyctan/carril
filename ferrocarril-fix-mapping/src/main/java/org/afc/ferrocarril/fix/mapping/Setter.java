package org.afc.ferrocarril.fix.mapping;


public interface Setter<S, T, V> {

	public void set(S source, T target, V value);

}
