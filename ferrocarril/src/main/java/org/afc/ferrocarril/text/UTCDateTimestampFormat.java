package org.afc.ferrocarril.text;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class UTCDateTimestampFormat extends SimpleDateFormat {

	private static final long serialVersionUID = 3757718682553213199L;

	public static final String PATTERN = "yyyyMMdd-HH:mm:ss.SSS";

	public static final String TIME_ZONE_CODE = "UTC";

	public UTCDateTimestampFormat() {
		super(PATTERN);
		setTimeZone(TimeZone.getTimeZone(TIME_ZONE_CODE));
	}
}
