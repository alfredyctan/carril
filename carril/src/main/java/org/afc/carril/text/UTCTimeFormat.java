package org.afc.carril.text;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class UTCTimeFormat extends SimpleDateFormat {

	private static final long serialVersionUID = -9019885990076220480L;

	public static final String PATTERN = "HH:mm:ss.SSS";
	
	public static final String TIME_ZONE_CODE = "UTC";
	
	public UTCTimeFormat() {
		super(PATTERN);
		setTimeZone(TimeZone.getTimeZone(TIME_ZONE_CODE));
	}
}
