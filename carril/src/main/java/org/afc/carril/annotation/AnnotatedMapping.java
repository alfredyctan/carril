package org.afc.carril.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Repeatable(AnnotatedMappings.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD})
public @interface AnnotatedMapping {

	public static enum Wire {
		Generic, Fix
	}

	public static enum Section {
		Header, Body, Trailer
	}
	
	/** type of wire format **/
	Wire wire() default Wire.Generic;

	/** section of the mapping **/
	Section section() default Section.Body;
	
	/** Unique name in the external wire format **/
	String name();

	/** Declared class in the object, normally will be same to implClass except List/Map...etc member **/
	Class<?> declare();

	/** Actual implementation class of the member **/
	Class<?> implement() default Void.class;

	/** Getter accessor Method object for reflection **/
	String getter() default "";

	/** Setter accessor Method object for reflection **/
	String setter() default "";

	/** Format Class for String type data conversion, default void **/
	Class<?> formatter() default Void.class;

	/** Format for String type data conversion **/
	String format() default "";
	
}
