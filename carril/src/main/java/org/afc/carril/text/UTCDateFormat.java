package org.afc.carril.text;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class UTCDateFormat extends SimpleDateFormat {

	private static final long serialVersionUID = -6045651195134816940L;

	public static final String PATTERN = "yyyyMMdd";
	
	public static final String TIME_ZONE_CODE = "UTC";
	
	public UTCDateFormat() {
		super(PATTERN);
		setTimeZone(TimeZone.getTimeZone(TIME_ZONE_CODE));
	}
}
