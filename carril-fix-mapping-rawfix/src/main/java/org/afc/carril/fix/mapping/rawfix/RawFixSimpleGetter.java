package org.afc.carril.fix.mapping.rawfix;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.text.UTCDateFormat;
import org.afc.carril.text.UTCDateTimestampFormat;
import org.afc.carril.text.UTCDatetimeFormat;
import org.afc.carril.text.UTCTimeFormat;
import org.afc.carril.transport.TransportException;

public class RawFixSimpleGetter implements Getter<RawFix> {
	
	private TypeConverter<?> converter;
	
	private String field;
	
	public RawFixSimpleGetter(String index, Type type) {
		field = index;
		switch (type) {
			case INT:
				converter = new IntTypeConverter();
				break;
			case STRING:
				converter = new StringTypeConverter();
				break;
			case DECIMAL:
				converter = new DecimalTypeConverter();
				break;
			case DOUBLE:
				converter = new DoubleTypeConverter();
				break;
			case DATETIMESTAMP:
				converter = new UTCDatetimestampConverter();
				break;	
			case DATETIME:
				converter = new UTCDatetimeTypeConverter();
				break;
			case DATE:
				converter = new UTCDateTypeConverter();
				break;
			case TIME:
				converter = new UTCTimeTypeConverter();
				break;
			case CHAR:
				converter = new CharTypeConverter();
				break;
			case BOOLEAN:
				converter = new BoolTypeConverter();
				break;
			case GROUP:
				converter = new RepeatingGroupTypeConverter();
				break;
			default:
				throw new TransportException("Unknown data type " + type); 
		}
    }
	
	@Override
	public Object get(RawFix source) {
		try {
			String value = source.findValue(field);
			if (value == null) {
				return null;
			}

			return converter.convert(value);
		} catch (Exception e) {
			throw new TransportException(e);
		}
	}

	private interface TypeConverter<T> {

		public T convert(String source) throws ParseException;

	}

	private class IntTypeConverter implements TypeConverter<Integer> {

		@Override
		public Integer convert(String source) {
			return Integer.valueOf(source);
		}
	}

	private class BoolTypeConverter implements TypeConverter<Boolean> {

		@Override
		public Boolean convert(String source) {
			return Boolean.valueOf(source);
		}
	}

	private class CharTypeConverter implements TypeConverter<Character> {

		@Override
		public Character convert(String source) {
			return Character.valueOf(source.charAt(0));
		}
	}

	private class DecimalTypeConverter implements TypeConverter<BigDecimal> {

		@Override
		public BigDecimal convert(String source) {
			return new BigDecimal(source);
		}
	}

	private class DoubleTypeConverter implements TypeConverter<Double> {

		@Override
		public Double convert(String source) {
			return Double.valueOf(source);
		}
	}

	private class StringTypeConverter implements TypeConverter<String> {

		@Override
		public String convert(String source) {
			return source;
		}
	}

	private class UTCTimeTypeConverter implements TypeConverter<Date> {

		private ThreadLocal<UTCTimeFormat> format = new ThreadLocal<UTCTimeFormat>() {
			protected UTCTimeFormat initialValue() {
				return new UTCTimeFormat();
			};
		};

		@Override
		public Date convert(String source) throws ParseException {
			return format.get().parse(source);
		}
	}

	private class UTCDateTypeConverter implements TypeConverter<Date> {

		private ThreadLocal<UTCDateFormat> format = new ThreadLocal<UTCDateFormat>() {
			protected UTCDateFormat initialValue() {
				return new UTCDateFormat();
			};
		};

		@Override
		public Date convert(String source) throws ParseException {
			return format.get().parse(source);
		}
	}

	private class UTCDatetimeTypeConverter implements TypeConverter<Date> {

		private ThreadLocal<UTCDatetimeFormat> format = new ThreadLocal<UTCDatetimeFormat>() {
			protected UTCDatetimeFormat initialValue() {
				return new UTCDatetimeFormat();
			};
		};

		@Override
		public Date convert(String source) throws ParseException {
			return format.get().parse(source);
		}
	}

	private class UTCDatetimestampConverter implements TypeConverter<Date> {

		private ThreadLocal<UTCDateTimestampFormat> format = new ThreadLocal<UTCDateTimestampFormat>() {
			protected UTCDateTimestampFormat initialValue() {
				return new UTCDateTimestampFormat();
			};
		};

		@Override
		public Date convert(String source) throws ParseException {
			return format.get().parse(source);
		}
	}

	private class RepeatingGroupTypeConverter implements TypeConverter<Integer> {

		@Override
		public Integer convert(String source) {
			return Integer.valueOf(source);
		}
	}
}
