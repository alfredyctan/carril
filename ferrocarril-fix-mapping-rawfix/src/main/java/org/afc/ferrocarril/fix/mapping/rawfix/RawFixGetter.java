package org.afc.ferrocarril.fix.mapping.rawfix;

import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.impl.AbstractTokenizedGetter;
import org.afc.ferrocarril.fix.mapping.schema.Type;

public class RawFixGetter extends AbstractTokenizedGetter<RawFix> {

	public RawFixGetter(String index, Type type) {
		super(index, type);
    }

	@Override
	protected Getter<RawFix> createGetter(String index, Type type) {
		return new RawFixSimpleGetter(index, type);
	}
}
