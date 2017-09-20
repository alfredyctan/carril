package org.afc.ferrocarril.fix.mapping.impl;

import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.schema.Type;
import org.afc.ferrocarril.message.FixMessage;

public class FixMessageGetter<F extends FixMessage> extends AbstractTokenizedGetter<F> {

	public FixMessageGetter(String index) {
		super(index, null);
    }

	@Override
	protected Getter<F> createGetter(String index, Type type) {
	    return new FixMessageSimpleGetter<F>(index);
	}
}
