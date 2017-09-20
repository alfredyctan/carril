package org.afc.ferrocarril.fix.mapping.quickfix;

import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.impl.AbstractTokenizedGetter;
import org.afc.ferrocarril.fix.mapping.schema.Type;

import quickfix.FieldMap;

public class QuickFixFieldGetter extends AbstractTokenizedGetter<FieldMap> {

	public QuickFixFieldGetter(String index, Type type) {
		super(index, type);
    }

	@Override
	protected Getter<FieldMap> createGetter(String index, Type type) {
		return new QuickFixSimpleGetter(index, type);
	}
}
