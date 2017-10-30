package org.afc.carril.fix.mapping.rawfix;

import java.util.Date;

import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.message.FixMessage;
import org.afc.carril.text.UTCDateFormat;
import org.afc.carril.text.UTCDateTimestampFormat;
import org.afc.carril.text.UTCDatetimeFormat;
import org.afc.carril.text.UTCTimeFormat;
import org.afc.carril.transport.TransportException;

public class RawFixSetter implements Setter<FixMessage, RawFix, Object> {

    @SuppressWarnings("unchecked")
    private TypeSetter setter;
	
	private String field;

	public RawFixSetter(String index, Type type) {
		this.field = index;
		switch (type) {
			case INT:
			case STRING:
			case DECIMAL:
			case DOUBLE:
			case CHAR:
			case BOOLEAN:
			case GROUP:
				setter = new ObjectTypeSetter();
				break;
			case DATETIMESTAMP:
				setter = new UTCDatetimestampTypeSetter();
				break;	
			case DATETIME:
				setter = new UTCDatetimeTypeSetter();
				break;
			case DATE:
				setter = new UTCDateTypeSetter();
				break;
			case TIME:
				setter = new UTCTimeTypeSetter();
				break;
			default:
				throw new TransportException("Unknown data type " + type); 
		}
    }

	@SuppressWarnings("unchecked")
    @Override
	public void set(FixMessage source, RawFix target, Object value) {
		try {
			if (value == null) {
				return;
			}
			
			setter.set(target, value);
        } catch (Exception e) {
	        throw new TransportException("Error on setting [" + field + "] = " + value, e);
        }
	}


	private interface TypeSetter<T> {

		public void set(RawFix target, T value);
	
	}

	private class ObjectTypeSetter implements TypeSetter<Object> {

		@Override
		public void set(RawFix target, Object value) {
			target.append(field, value.toString());
		}		
	}
	private class UTCTimeTypeSetter implements TypeSetter<Date> {

		private ThreadLocal<UTCTimeFormat> format = new ThreadLocal<UTCTimeFormat>() {
			protected UTCTimeFormat initialValue() {
				return new UTCTimeFormat();
			};
		};
		
		@Override
		public void set(RawFix target, Date value) {
			target.append(field, format.get().format(value));
		}		
	}

	private class UTCDateTypeSetter implements TypeSetter<Date> {

		private ThreadLocal<UTCDateFormat> format = new ThreadLocal<UTCDateFormat>() {
			protected UTCDateFormat initialValue() {
				return new UTCDateFormat();
			};
		};

		@Override
		public void set(RawFix target, Date value) {
			target.append(field, format.get().format(value));
		}		
	}

	private class UTCDatetimeTypeSetter implements TypeSetter<Date> {

		private ThreadLocal<UTCDatetimeFormat> format = new ThreadLocal<UTCDatetimeFormat>() {
			protected UTCDatetimeFormat initialValue() {
				return new UTCDatetimeFormat();
			};
		};

		@Override
		public void set(RawFix target, Date value) {
			target.append(field, format.get().format(value));
		}		
	}
	
	private class UTCDatetimestampTypeSetter implements TypeSetter<Date> {

		private ThreadLocal<UTCDateTimestampFormat> format = new ThreadLocal<UTCDateTimestampFormat>() {
			protected UTCDateTimestampFormat initialValue() {
				return new UTCDateTimestampFormat();
			};
		};

		@Override
		public void set(RawFix target, Date value) {
			target.append(field, format.get().format(value));
		}		
	}
}
