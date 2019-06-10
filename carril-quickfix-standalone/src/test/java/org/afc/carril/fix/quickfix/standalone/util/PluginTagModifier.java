package org.afc.carril.fix.quickfix.standalone.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.afc.carril.fix.quickfix.standalone.util.modifier.TagModifier;

import quickfix.Message;

public class PluginTagModifier implements TagModifier {

	public static final Pattern DOUBLE = Pattern.compile("^\\s*?\\$double\\((?<dbl>.*)\\)$");

	@Override
	public Pattern pattern() {
		return DOUBLE;
	}
	
	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		String dbl = matcher.group("dbl");
		return replace(str, dbl + dbl);
	}
}
