package org.afc.carril.text;

import java.math.BigDecimal;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class BigDecimalFormat extends Format {

	private static final long serialVersionUID = -7385010138608815002L;

	public BigDecimalFormat() {
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		return toAppendTo.append(obj.toString());
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		pos.setIndex(source.length());
		return new BigDecimal(source);
	}

}
