package org.afc.carril.fix.quickfix.standalone.util.modifier;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.afc.carril.fix.quickfix.standalone.util.FixTagResolver;

import org.afc.util.FileUtil;

import quickfix.Message;

public class LinkModifier implements TagModifier {

	private static final Pattern LINK = Pattern.compile("^\\s*?\\$link\\s*?\\((?<link>.*)\\)\\s*?$");
	
	@Override
	public Pattern pattern() {
		return LINK;
	}
	
	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		try {
			String link = matcher.group("link").trim();
			return FixTagResolver.resolve(new StringBuilder(FileUtil.readFileAsString(link)), message);
		} catch(Exception e) {
			return str;
		}
	}
}	
