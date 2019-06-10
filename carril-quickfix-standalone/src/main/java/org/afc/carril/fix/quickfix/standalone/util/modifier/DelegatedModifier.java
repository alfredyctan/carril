package org.afc.carril.fix.quickfix.standalone.util.modifier;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.afc.util.ListUtil;
import org.afc.util.ObjectUtil;

import quickfix.Message;

public class DelegatedModifier implements TagModifier {
	
	private static List<Class<? extends TagModifier>> pluginsModifiers = new ArrayList<>();

	private List<TagModifier> tagModifiers;
	
	public DelegatedModifier() {
		this.tagModifiers = ListUtil.list(new LinkedList<>(),
			new FieldModifier(),
			new GroupModifier(),
			new PutModifier(),
			new GetModifier(),
			new ScaleModifier(),
			new RandModifier(),
			new LenModifier(),
			new XPathModifier(),
			new DateModifier(),
			new ArithmeticModifier(),
			new LinkModifier()
		); 
		this.tagModifiers.addAll(
			pluginsModifiers.stream().map(ObjectUtil::newInstance).collect(Collectors.toList())
		);
	}
	
	@Override
	public Pattern pattern() {
		return null;
	}
	
	@Override
	public StringBuilder modify(StringBuilder str, Message message, Matcher matcher) {
		for (TagModifier tagModifier : tagModifiers) {
			matcher = tagModifier.pattern().matcher(str);
			if (matcher.matches()) {
				return tagModifier.modify(str, message, matcher);
			}
		}
		return str;
		
//		if ((matcher = TAG.matcher(str)).matches()) {
//			return fieldModifier.modify(str, message, matcher);
//		} else if ((matcher = GROUP.matcher(str)).matches()) {
//			return groupModifier.modify(str, message, matcher);
//		} else if ((matcher = PUT.matcher(str)).matches()) {
//			return putModifier.modify(str, message, matcher);
//		} else if ((matcher = GET.matcher(str)).matches()) {
//			return getModifier.modify(str, message, matcher);
//		} else if ((matcher = SCALE.matcher(str)).matches()) {
//			return scaleModifier.modify(str, message, matcher);
//		} else if ((matcher = RAND.matcher(str)).matches()) {
//			return randModifier.modify(str, message, matcher);
//		} else if ((matcher = LENGTH.matcher(str)).matches()) {
//			return lenModifier.modify(str, message, matcher);
//		} else if ((matcher = XPATH.matcher(str)).matches()) {
//			return xpathModifier.modify(str, message, matcher);
//		} else if (!str.toString().matches("\\d\\d\\d\\d-\\d\\d-\\d\\d") && (matcher = ARTH.matcher(str)).matches()) {
//			return arithmeticModifier.modify(str, message, matcher);
//		} else if ((matcher = DATE.matcher(str)).matches()) {
//			return dateModifier.modify(str, message, matcher);
//		} else if ((matcher = LINK.matcher(str)).matches()) {
//			return linkModifier.modify(str, message, matcher);
//		} else {
//			@AllArgsConstructor
//			class Matched {
//				TagModifier modifier;
//				Matcher matcher;
//			}
//			
//			Matched matched = pluginsModifiers.entrySet().stream().map(e -> {
//				Matcher m; 
//				if ((m = e.getKey().matcher(str)).matches()) {
//					return new Matched(e.getValue(), m);
//				} else {
//					return null;
//				}
//			}).filter(p -> p != null).findFirst().orElse(null);
//			
//			return (matched != null) ? matched.modifier.modify(str, message, matched.matcher) : str;
//		}
	}

	public static void addPluginModifier(Class<? extends TagModifier> clazz) {
		pluginsModifiers.add(clazz);
	}
}
