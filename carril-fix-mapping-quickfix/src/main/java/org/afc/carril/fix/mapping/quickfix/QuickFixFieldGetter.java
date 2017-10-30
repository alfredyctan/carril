package org.afc.carril.fix.mapping.quickfix;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.impl.AbstractTokenizedGetter;
import org.afc.carril.fix.mapping.schema.Type;

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
