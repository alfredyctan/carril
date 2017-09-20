package org.afc.ferrocarril.fix.mapping;

public interface SessionState {

	public Object get(String key);

	public void put(String key, Object value);
	
}
