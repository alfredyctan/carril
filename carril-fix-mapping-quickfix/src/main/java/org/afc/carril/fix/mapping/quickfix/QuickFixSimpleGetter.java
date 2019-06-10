package org.afc.carril.fix.mapping.quickfix;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.transport.FieldNotFoundException;
import org.afc.carril.transport.TransportException;

import quickfix.BytesField;
import quickfix.FieldMap;
import quickfix.FieldNotFound;

public class QuickFixSimpleGetter implements Getter<FieldMap> {
	
	private static interface TypeGetter<T> {

		public T get(FieldMap source) throws FieldNotFound;

	}

	private TypeGetter<?> getter;
	
	private int tagNum;
	
	public QuickFixSimpleGetter(String index, Type type) {
		tagNum = Integer.valueOf(index);
		switch (type) {
			case INT:
				getter = (source) -> source.getInt(tagNum);
				break;
			case STRING:
				getter = (source) -> source.getString(tagNum);
				break;
			case DECIMAL:
				getter = (source) -> source.getDecimal(tagNum);
				break;
			case DOUBLE:
				getter = (source) -> source.getDouble(tagNum);
				break;
			case DATETIMESTAMP:
				getter = (source) -> source.getUtcTimeStamp(tagNum);
				break;
			case DATETIME:
				getter = (source) -> source.getUtcTimeStamp(tagNum);
				break;
			case DATE:
				getter = (source) -> source.getUtcDateOnly(tagNum);
				break;
			case TIME:
				getter = (source) -> source.getUtcTimeOnly(tagNum);
				break;
			case TIMESTAMP:
				getter = (source) -> source.getUtcTimeOnly(tagNum);
				break;
			case CHAR:
				getter = (source) -> source.getChar(tagNum);
				break;
			case BOOLEAN:
				getter = (source) -> source.getBoolean(tagNum);
				break;
			case BYTES:
				getter = (source) -> (source.getField(new BytesField(tagNum))).getValue();
				break;
			case GROUP:
				getter = (source) -> source.getGroups(tagNum);
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
			throw new FieldNotFoundException(e.getMessage());
		} catch (Exception e) {
			throw new TransportException(e);
		}
	}
}
