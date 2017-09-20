package org.afc.ferrocarril.fix.mapping.impl;

import java.util.ArrayList;
import java.util.List;

import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.schema.Type;

public abstract class AbstractTokenizedGetter<S> implements Getter<S> {

	private static final String PREFix = "${";
	
	private static final String SUFFix = "}";

	private String index;
	
	private Getter<S>[] getters;
	
	private String[] tokens;
	
	private boolean single;
	
	@SuppressWarnings("unchecked")
    public AbstractTokenizedGetter(String index, Type type) {
		this.index = index;

		List<Getter<S>> nameList = new ArrayList<Getter<S>>();
		List<String> tokenList = new ArrayList<String>();
		
		int startIndex = index.indexOf(PREFix);
		int endIndex = -1; 
		while (startIndex != -1) {
			tokenList.add(index.substring(endIndex + 1, startIndex));
			endIndex = findPlaceholderEndIndex(startIndex);
			if (endIndex == -1) {
				break;
			}
			nameList.add(createGetter(index.substring(startIndex + PREFix.length(), endIndex), type));
			startIndex = index.indexOf(PREFix, endIndex + 1);
		}
		if (endIndex != index.length() - 1) {
			tokenList.add(index.substring(endIndex + 1, index.length()));
		}
		
		getters = new Getter[nameList.size()]; 
		getters = nameList.toArray(getters);

		tokens = new String[tokenList.size()]; 
		tokens = tokenList.toArray(tokens);

		single = (tokens.length == 1 && "".equals(tokens[0]));
    }

	private int findPlaceholderEndIndex(int startIndex) {
		return index.indexOf(SUFFix, startIndex);
	}
		
		
	@Override
	public Object get(S source) {
		if (single) {
			return getters[0].get(source);
		} else {
			StringBuilder builder = new StringBuilder();
			for (int i = 0; i < tokens.length; i++) {
				builder.append(tokens[i]);
				if (i < getters.length) {
					builder.append(getters[i].get(source));
				}
			}
			return builder.toString();
		}
	}

	protected abstract Getter<S> createGetter(String index, Type type);
}
