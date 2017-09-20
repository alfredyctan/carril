package org.afc.ferrocarril.fix.mapping.rawfix;

public class RawFixReader extends RawFix {

	private int index;

	private String value;
	
	public RawFixReader(String value) {
		this.value = value;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public String findValueFrom(String id, int index) {
		setIndex(index);
		return findValue(id);
	}
	
	public String findValue(String id) {
		int fromIndex = index;
		String name = id + EQ;
		int nameLength = name.length();

		int beginIndex = value.indexOf(name, fromIndex);
		if (beginIndex == -1) {
			return null;
		} else {
			if (beginIndex != 0 && value.charAt(beginIndex - 1) != SOH) {
				index = value.indexOf(SOH, beginIndex) + 1; //search from next pair
				if (index == 0) {
					index = fromIndex; // cannot found any SOH in the string
					return null;
				}
				String found = findValue(id); 
				if (found == null) { 
					index = fromIndex; //reset to original index if not found
					return null;
				} else {
					return found;
				}
			}
		}
		
		int endIndex = value.indexOf(SOH, beginIndex);
		if (endIndex == -1) {
			return null;
		}
		
		int valueBegin = beginIndex + nameLength;
		String found = value.substring(valueBegin, endIndex);
		index = valueBegin + found.length() + 1;
		return found;
	}	
	
	@Override
	public String toString() {
		return value;
	}

	@Override
    public RawFix append(String id, String v) {
	    throw new UnsupportedOperationException("Operation not supported in RawFixReader.");
    }

	@Override
    public void seal() {
	    throw new UnsupportedOperationException("Operation not supported in RawFixReader.");
    }
}
