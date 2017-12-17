package org.afc.carril.fix.mapping.quickfix;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.transport.TransportException;

import quickfix.FieldMap;
import quickfix.FieldNotFound;

public class QuickFixSimpleGetter implements Getter<FieldMap> {
	
	private static interface TypeGetter<T> {

		public T get(FieldMap source) throws FieldNotFound;

	}

	private TypeGetter<?> getter;
	
	private int field;
	
	public QuickFixSimpleGetter(String index, Type type) {
		field = Integer.valueOf(index);
		switch (type) {
			case INT:
				getter = (source) -> source.getInt(field);
				break;
			case STRING:
				getter = (source) -> source.getString(field);
				break;
			case DECIMAL:
				getter = (source) -> source.getDecimal(field);
				break;
			case DOUBLE:
				getter = (source) -> source.getDouble(field);
				break;
			case DATETIMESTAMP:
				getter = (source) -> source.getUtcTimeStamp(field);
				break;
			case DATETIME:
				getter = (source) -> source.getUtcTimeStamp(field);
				break;
			case DATE:
				getter = (source) -> source.getUtcDateOnly(field);
				break;
			case TIME:
				getter = (source) -> source.getUtcTimeOnly(field);
				break;
			case CHAR:
				getter = (source) -> source.getChar(field);
				break;
			case BOOLEAN:
				getter = (source) -> source.getBoolean(field);
				break;
			case GROUP:
				getter = (source) -> source.getGroups(field);
				break;
			default:
				throw new TransportException("Unknown data type " + type); 
		}
    }
	
	@Override
	public Object get(FieldMap source) {
		try {
			return getter.get(source);
		} catch (FieldNotFound e) {
			throw new TransportException(e.getMessage());
		} catch (Exception e) {
			throw new TransportException(e);
		}
	}
}
