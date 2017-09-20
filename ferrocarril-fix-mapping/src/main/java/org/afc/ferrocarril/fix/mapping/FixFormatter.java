package org.afc.ferrocarril.fix.mapping;

import org.afc.ferrocarril.message.FixMessage;

public interface FixFormatter<S extends FixMessage, T> {

	public T format(S source);
	
}
