package org.afc.carril.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQuery;

public abstract class AbstractTemporalFormat<T extends TemporalAccessor> extends Format {

	private static final long serialVersionUID = 2615561447044601179L;

	@Override
	public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
		dateTimeFormatter().formatTo((TemporalAccessor)obj, toAppendTo);
		return toAppendTo;
	}

	@Override
	public Object parseObject(String source, ParsePosition pos) {
		return dateTimeFormatter().parse(source, pos).query(temporalQuery());
	}
	
	@SuppressWarnings("unchecked")
	public T parse(String source) {
		return (T)parseObject(source, new ParsePosition(0));
	}
	
	protected abstract TemporalQuery<T> temporalQuery();
	
	protected abstract DateTimeFormatter dateTimeFormatter(); 

}
