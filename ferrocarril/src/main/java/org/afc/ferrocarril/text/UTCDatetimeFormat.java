package org.afc.ferrocarril.text;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class UTCDatetimeFormat extends SimpleDateFormat {

	private static final long serialVersionUID = 4809117784279500264L;

	public static final String PATTERN = "yyyyMMdd-HH:mm:ss";
	
	public static final String TIME_ZONE_CODE = "UTC";
	
	public UTCDatetimeFormat() {
		super(PATTERN);
		setTimeZone(TimeZone.getTimeZone(TIME_ZONE_CODE));
	}
}
