package org.afc.ferrocarril.fix.mapping;



public interface MsgMapper<S, T> {

	public String match(S source);

	public T map(S source, T target);

}
