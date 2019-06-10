package org.afc.carril.fix.mapping.impl;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.schema.Type;

public class FixMessageGetter<F> extends AbstractTokenizedGetter<F> {

	public FixMessageGetter(String index) {
		super(index, null);
    }

	@Override
	protected Getter<F> createGetter(String index, Type type) {
	    return new FixMessageSimpleGetter<F>(index);
	}
}
