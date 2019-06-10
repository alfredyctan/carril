package org.afc.carril.text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQuery;

public class FixDateTimestampFormat extends AbstractTemporalFormat<LocalDateTime> {

	private static final long serialVersionUID = -5564746406348846293L;

	public static final String PATTERN = "yyyyMMdd-HH:mm:ss.SSS";

	private TemporalQuery<LocalDateTime> temporalQuery;

	private DateTimeFormatter dateTimeFormatter;
	
	public FixDateTimestampFormat() {
		this.temporalQuery = LocalDateTime::from;
		this.dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN);
	}

	@Override
	protected TemporalQuery<LocalDateTime> temporalQuery() {
		return temporalQuery;
	}

	@Override
	protected DateTimeFormatter dateTimeFormatter() {
		return dateTimeFormatter;
	}
}
