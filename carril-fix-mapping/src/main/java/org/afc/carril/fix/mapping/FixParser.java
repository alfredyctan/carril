package org.afc.carril.fix.mapping;

import org.afc.carril.message.FixMessage;

public interface FixParser<S, T extends FixMessage> {

	public T parse(S source, Class<T> clazz);
}
