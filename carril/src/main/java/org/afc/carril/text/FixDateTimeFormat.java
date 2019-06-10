package org.afc.carril.text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalQuery;

public class FixDateTimeFormat extends AbstractTemporalFormat<LocalDateTime> {

	private static final long serialVersionUID = -8424602933887328082L;

	public static final String PATTERN = "yyyyMMdd-HH:mm:ss";
	
	private TemporalQuery<LocalDateTime> temporalQuery;

	private DateTimeFormatter dateTimeFormatter;
	
	public FixDateTimeFormat() {
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
