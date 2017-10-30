package org.afc.carril.fix.mapping.impl;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StateSetter<S, T> implements Setter<S, T, Object> {

	private static final Logger logger = LoggerFactory.getLogger(StateSetter.class);

	private Getter<S> getter;

	private SessionState state;
	
	public StateSetter(SessionState state, Getter<S> sourceGetter) {
		this.state = state;
		this.getter = sourceGetter;
	}
	
	@Override
	public void set(S source, T target, Object value) {
		if (value == null) {
			return;
		}

		Object index = getter.get(source);
		if (index != null) {
			state.put(index.toString(), value);
			logger.trace("setting state : {} -> {}", index, value);
		}
	}
}
