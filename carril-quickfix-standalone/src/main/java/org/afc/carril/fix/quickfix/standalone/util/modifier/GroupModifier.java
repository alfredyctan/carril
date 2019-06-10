package org.afc.carril.fix.quickfix.standalone.util.modifier;

import static org.afc.carril.fix.quickfix.text.MessageUtil.*;
import static java.lang.Integer.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import quickfix.Group;
import quickfix.Message;

public class GroupModifier implements TagModifier {

	private static final Pattern GROUP = Pattern.compile("^\\s*?#(?<groupTag>\\d*)\\.(?<num>\\d*)\\.(?<tag>\\d*?)\\s*?$");

	@Override
	public Pattern pattern() {
		return GROUP;
	}
	
	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			Group group = getGroup(message, parseInt(matcher.group("num")), parseInt(matcher.group("groupTag")));
			String value = getString(group, parseInt(matcher.group("tag")));
			return replace(str, value);
		} catch (Exception e) {
			return str;
		}
	}
}
