package org.afc.ferrocarril.fix.mapping;

import org.afc.ferrocarril.message.FixMessage;

public interface FixParser<S, T extends FixMessage> {

	public T parse(S source, Class<T> clazz);
}
