package org.afc.carril.fix.quickfix.standalone.util.modifier;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.afc.carril.text.FixDateFormat;
import org.afc.carril.text.FixDateTimeFormat;
import org.afc.carril.text.FixDateTimestampFormat;
import org.afc.util.DateUtil;

import quickfix.Message;

public class DateModifier implements TagModifier {

	private static final Pattern DATE = Pattern.compile("^\\s*?\\$date\\s*?\\(\\s*?(?<date>.*)\\s*?,\\s*?(?<delta>-?\\d*[D|M|Y|h|m|s|S]?)\\s*?\\)\\s*?$");

	private static final FixDateTimestampFormat fixDateTimestampFormat = new FixDateTimestampFormat(); 
	
	private static final FixDateTimeFormat fixDateTimeFormat = new FixDateTimeFormat(); 
	
	private static final FixDateFormat fixDateFormat = new FixDateFormat(); 
	
	@Override
	public Pattern pattern() {
		return DATE;
	}
	
	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			String dateStr = matcher.group("date").trim();
			String daysStr = matcher.group("delta").trim();
			String value = tryXMLDatetime(dateStr, daysStr);
			if (value != null) {
				return replace(str, value);
			}
			value = tryLocalDate(dateStr, daysStr);
			if (value != null) {
				return replace(str, value);
			}
			value = tryFixDateTimestamp(dateStr, daysStr);
			if (value != null) {
				return replace(str, value);
			}
			value = tryFixDatetime(dateStr, daysStr);
			if (value != null) {
				return replace(str, value);
			}
			value = tryFixDate(dateStr, daysStr);
			if (value != null) {
				return replace(str, value);
			}
			return str;
		} catch(Exception e) {
			return str;
		}
	}

	private static String tryFixDateTimestamp(String dateStr, String daysStr) {
		try {
			LocalDateTime date = fixDateTimestampFormat.parse(dateStr);
			return fixDateTimestampFormat.format(temporalAdd(date, daysStr));
		} catch (Exception e) {
			return null;
		}
	}

	private static String tryFixDatetime(String dateStr, String daysStr) {
		try {
			LocalDateTime date = fixDateTimeFormat.parse(dateStr);
			return fixDateTimeFormat.format(temporalAdd(date, daysStr));
		} catch (Exception e) {
			return null;
		}
	}

	private static String tryFixDate(String dateStr, String daysStr) {
		try {
			LocalDate date = fixDateFormat.parse(dateStr);
			return fixDateFormat.format(temporalAdd(date, daysStr));
		} catch (Exception e) {
			return null;
		}
	}

	private static String tryXMLDatetime(String dateStr, String daysStr) {
		try {
			Date date = DateUtil.parseXMLDatetime(dateStr);
			if (daysStr.endsWith("M")) {
				int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
				return DateUtil.formatXMLDatetime(DateUtil.dateAdd(DateUtil.MONTH, date, amt));
			} else if (daysStr.endsWith("Y")) {
				int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
				return DateUtil.formatXMLDatetime(DateUtil.dateAdd(DateUtil.YEAR, date, amt));
			} else if (daysStr.endsWith("D")) {
				int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
				return DateUtil.formatXMLDatetime(DateUtil.dateAdd(DateUtil.DAY, date, amt));
			} else if (daysStr.endsWith("h")) {
				int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
				return DateUtil.formatXMLDatetime(DateUtil.dateAdd(DateUtil.HOUR, date, amt));
			} else if (daysStr.endsWith("m")) {
				int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
				return DateUtil.formatXMLDatetime(DateUtil.dateAdd(DateUtil.MINUTE, date, amt));
			} else if (daysStr.endsWith("s")) {
				int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
				return DateUtil.formatXMLDatetime(DateUtil.dateAdd(DateUtil.SECOND, date, amt));
			} else if (daysStr.endsWith("S")) {
				int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
				return DateUtil.formatXMLDatetime(DateUtil.dateAdd(DateUtil.MILLISECOND, date, amt));
			} else {
				return dateStr;
			}
		} catch (Exception e) {
			return null;
		}
	}

	private static String tryLocalDate(String dateStr, String daysStr) {
		try {
			LocalDate date = DateUtil.localDate(dateStr);
			return temporalAdd(date, daysStr).toString();
		} catch (Exception e) {
			return null;
		}
	}

	private static <T extends Temporal> T temporalAdd(Temporal date, String daysStr) {
		if (daysStr.endsWith("M")) {
			int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
			return (T) date.plus(amt, ChronoUnit.MONTHS);
		} else if (daysStr.endsWith("Y")) {
			int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
			return (T) date.plus(amt, ChronoUnit.YEARS);
		} else if (daysStr.endsWith("D")) {
			int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
			return (T)date.plus(amt, ChronoUnit.DAYS);
		} else if (daysStr.endsWith("h")) {
			int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
			return (T)date.plus(amt, ChronoUnit.HOURS);
		} else if (daysStr.endsWith("m")) {
			int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
			return (T)date.plus(amt, ChronoUnit.MINUTES);
		} else if (daysStr.endsWith("s")) {
			int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
			return (T)date.plus(amt, ChronoUnit.SECONDS);
		} else if (daysStr.endsWith("S")) {
			int amt = Integer.parseInt(daysStr.substring(0, daysStr.length() - 1));
			return (T)date.plus(amt, ChronoUnit.MILLIS);
		} else {
			return (T)date;
		}
	}
}	
