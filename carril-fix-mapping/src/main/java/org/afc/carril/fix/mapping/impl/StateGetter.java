package org.afc.carril.fix.mapping.impl;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.SessionState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StateGetter<S> implements Getter<S> {

	private static final Logger logger = LoggerFactory.getLogger(StateGetter.class);

	private Getter<S> getter;
	
	private SessionState state;
	
	public StateGetter(SessionState state, Getter<S> sourceGetter) {
		this.state = state;
		this.getter = sourceGetter;
	}

	@Override
	public Object get(S source) {
		Object index = getter.get(source);
		if (index == null) {
			return null;
		} else {
			Object value = state.get(index.toString());
			logger.trace("getting state : {} -> {}", index, value);
			return value;
		}
	}
}
