package org.afc.carril.transport;

import java.lang.reflect.Method;
import java.text.Format;

import org.afc.carril.annotation.Carril.Section;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccessorMapping {
	
	/* Field name in the external wire format name */
	private String fieldName;

	/* part/section in the external wire format */
	private Section section;
	
	/* Getter accessor Method object for reflection */
	private Method getMethod;

	/* Setter accessor Method object for reflection */
	private Method setMethod;

	/* Declared class in the object, normally will be same to implClass except List/Map...etc member */
	private Class<?> declareClass;

	/* Actual implementation class of the member */
	private Class<?> implClass;

	/* Format for String type data conversion */
	private Format format;

	public AccessorMapping(String fieldName, Section section, Method getMethod, Method setMethod, Class<?> declareClass, Class<?> implClass) {
		this(fieldName, section, getMethod, setMethod, declareClass, implClass, null);
	}
}
