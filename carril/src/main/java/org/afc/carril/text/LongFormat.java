package org.afc.carril.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

public class LongFormat extends Format {

	private static final long serialVersionUID = 846698232765008765L;

	public LongFormat() {
	}

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		return toAppendTo.append(String.valueOf(obj));
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		pos.setIndex(source.length());
		return Long.valueOf(source);
	}
}
