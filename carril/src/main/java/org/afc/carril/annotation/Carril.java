package org.afc.carril.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.Accessors;

@Repeatable(Carrils.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD })
public @interface Carril {

	@Getter
	@Accessors(fluent = true)
	@AllArgsConstructor
	public static enum Wire {
		
		Generic(0), Fix(1);
		
		private int index;
	}

	@Getter
	@Accessors(fluent = true)
	@AllArgsConstructor
	public static enum Section {
		
		Header(0), Body(1), Trailer(2);
		
		private int index;
	}

	/** type of wire format **/
	Wire wire() default Wire.Generic;

	/** section of the mapping **/
	Section section() default Section.Body;

	/**
	 * Unique name in the external wire format, default will be the name of the
	 * field
	 **/
	String name() default "";

	/**
	 * Getter accessor Method object for reflection, default will be the getter
	 * method name
	 **/
	String getter() default "";

	/**
	 * Setter accessor Method object for reflection, default will be the setter
	 * method name
	 **/
	String setter() default "";

	/**
	 * Declared class in the object, normally will be same to implClass except
	 * List/Map...etc member
	 **/
	Class<?> declare() default Void.class;

	/** Actual implementation class of the member **/
	Class<?> implement() default Void.class;

	/**
	 * Sub-Class of java.text.Format for String type data conversion, default void
	 **/
	Class<?> format() default Void.class;

	/** Format for String type data conversion **/
	String pattern() default "";

}
