package org.afc.carril.fix.quickfix.standalone.util.modifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickfix.Message;

public interface TagModifier {
	
	public Pattern pattern();
	
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher);
	
	default public StringBuilder replace(StringBuilder str, String value) {
		return (value != null) ? str.replace(0, str.length(), value) : str;
	}
	
}
