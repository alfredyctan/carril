package org.afc.carril.fix.mapping.rawfix;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.impl.AbstractTokenizedGetter;
import org.afc.carril.fix.mapping.schema.Type;

public class RawFixGetter extends AbstractTokenizedGetter<RawFix> {

	public RawFixGetter(String index, Type type) {
		super(index, type);
    }

	@Override
	protected Getter<RawFix> createGetter(String index, Type type) {
		return new RawFixSimpleGetter(index, type);
	}
}
