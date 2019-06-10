package org.afc.carril.fix.quickfix.standalone.util.modifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickfix.Message;

public class LenModifier implements TagModifier {

	private static final Pattern LENGTH = Pattern.compile("^\\s*?\\$len\\s*?\\((?<text>.*)\\)\\s*?$");

	@Override
	public Pattern pattern() {
		return LENGTH;
	}

	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			return replace(str, String.valueOf(matcher.group("text").length()));
		} catch(Exception e) {
			return str;
		}
	}
}
