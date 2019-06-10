package org.afc.carril.text;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQuery;

public class FixTimestampFormat extends AbstractTemporalFormat<LocalTime> {

	private static final long serialVersionUID = -6914458614984513400L;

	public static final String PATTERN = "HH:mm:ss.SSS";
	
	private TemporalQuery<LocalTime> temporalQuery;

	private DateTimeFormatter dateTimeFormatter;
	
	public FixTimestampFormat() {
		this.temporalQuery = LocalTime::from;
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN);
	}

	@Override
	protected TemporalQuery<LocalTime> temporalQuery() {
		return temporalQuery;
	}

	@Override
	protected DateTimeFormatter dateTimeFormatter() {
		return dateTimeFormatter;
	}
}
