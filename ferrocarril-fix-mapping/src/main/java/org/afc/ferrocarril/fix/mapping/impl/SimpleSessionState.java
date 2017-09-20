package org.afc.ferrocarril.fix.mapping.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.afc.ferrocarril.fix.mapping.SessionState;

public class SimpleSessionState implements SessionState {

	private Map<String, Object> map;
	
	public SimpleSessionState() {
		this.map = new ConcurrentHashMap<String, Object>();
    }
	
	@Override
	public Object get(String key) {
	    return map.get(key);
	}

	@Override
	public void put(String key, Object value) {
		map.put(key, value);
	}
}
