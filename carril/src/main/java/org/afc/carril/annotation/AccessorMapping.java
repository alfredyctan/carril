package org.afc.carril.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.SimpleDateFormat;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AccessorMapping {

	/** Unique name in the external wire format **/
	String name();

	/** Declared class in the object, normally will be same to implClass except List/Map...etc member **/
	Class<?> declare();

	/** Actual implementation class of the member **/
	Class<?> implement() default AccessorMapping.class;

	/** Getter accessor Method object for reflection **/
	String getter() default "";

	/** Setter accessor Method object for reflection **/
	String setter() default "";

	/** Format Class for String type data conversion, default java.text.SimpleDateFormat **/
	Class<?> formatter() default SimpleDateFormat.class;

	/** Format for String type data conversion **/
	String format() default "";
	
}
