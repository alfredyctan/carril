package org.afc.ferrocarril.fix.mapping;

public interface TagMapper<S, T> {

	public T map(S source, T target);
	
	public void addTagMapper(TagMapper<S, T> tagMapper);
	
}
