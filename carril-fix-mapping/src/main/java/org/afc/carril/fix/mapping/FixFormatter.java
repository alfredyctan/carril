package org.afc.carril.fix.mapping;

import org.afc.carril.message.FixMessage;

public interface FixFormatter<S extends FixMessage, T> {

	public T format(S source);
	
}
