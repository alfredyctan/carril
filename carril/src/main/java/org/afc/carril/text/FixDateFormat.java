package org.afc.carril.text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQuery;

public class FixDateFormat extends AbstractTemporalFormat<LocalDate> {

	private static final long serialVersionUID = -6892400572817206613L;

	public static final String PATTERN = "yyyyMMdd";
	
	private TemporalQuery<LocalDate> temporalQuery;

	private DateTimeFormatter dateTimeFormatter;
	
	public FixDateFormat() {
		this.temporalQuery = LocalDate::from;
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN);
	}

	@Override
	protected TemporalQuery<LocalDate> temporalQuery() {
		return temporalQuery;
	}
	
	@Override
	protected DateTimeFormatter dateTimeFormatter() {
		return dateTimeFormatter;
	}
}
