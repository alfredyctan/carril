package org.afc.carril.fix.mapping.quickfix;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.BiConsumer;

import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.transport.TransportException;

import quickfix.FieldMap;
import quickfix.Group;
import quickfix.Message;
import quickfix.UtcTimestampPrecision;

public class QuickFixFieldSetter implements Setter<Object, FieldMap, Object> {
	
    private BiConsumer<FieldMap, Object> setter;
	
	private int tagNum;
	
	public QuickFixFieldSetter(String index, Type type, List<String> order) {
		tagNum = Integer.valueOf(index);
		switch (type) {
			case INT:
				setter = (target, value) -> target.setInt(tagNum, (int)value);
				break;
			case STRING:
				setter = (target, value) -> target.setString(tagNum, (String)value);
				break;
			case DECIMAL:
				setter = (target, value) -> target.setDecimal(tagNum, (BigDecimal)value);
				break;
			case DOUBLE:
				setter = (target, value) -> target.setDouble(tagNum, (double)value);
				break;
			case DATETIMESTAMP:
				setter = (target, value) -> target.setUtcTimeStamp(tagNum, (LocalDateTime)value, UtcTimestampPrecision.MILLIS);
				break;
			case DATETIME:
				setter = (target, value) -> target.setUtcTimeStamp(tagNum, (LocalDateTime)value, false);
				break;
			case DATE:
				setter = (target, value) -> target.setUtcDateOnly(tagNum, (LocalDate)value);
				break;
			case TIME:
				setter = (target, value) -> target.setUtcTimeOnly(tagNum, (LocalTime)value, false);
				break;
			case TIMESTAMP:
				setter = (target, value) -> target.setUtcTimeOnly(tagNum, (LocalTime)value, true);
				break;
			case CHAR:
				setter = (target, value) -> target.setChar(tagNum, (char)value);
				break;
			case BOOLEAN:
				setter = (target, value) -> target.setBoolean(tagNum, (boolean)value);
				break;
			case BYTES:
				setter = (target, value) -> target.setBytes(tagNum, (byte[])value);
				break;
			case GROUP:
				setter = (target, value) -> {
					Message message = (Message)value;
					int[] tagOrder = order.stream().mapToInt(v -> Integer.parseInt(v)).toArray();
					Group group = new Group(tagNum, tagOrder[0], tagOrder);
					group.setFields(message);
					group.setGroups(message);
					target.addGroup(group);
				};
				break;
			default:
				throw new TransportException("Unknown data type " + type); 
		}
    }
	
	@Override
	public void set(Object source, FieldMap target, Object value) {
		try {
			if (value == null) {
				return;
			}
			setter.accept(target, value);
		} catch (Exception e) {
			throw new TransportException("Error on setting [" + tagNum + "] = " + value, e);
		}
	}
}
