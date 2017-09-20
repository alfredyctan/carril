package org.afc.ferrocarril.fix.mapping.rawfix;


public abstract class RawFix {

	protected static final char EQ = '=';

	protected static final char SOH = '\01';

	public abstract int getIndex();

	public abstract void setIndex(int index);

	public abstract String findValueFrom(String id, int index);

	public abstract String findValue(String id);

	public abstract RawFix append(String id, String v);

	public abstract void seal();

	public static RawFix forRead(String value) {
		return new RawFixReader(value);
	}
	
	public static RawFix forWrite(String beginString, String msgType, String senderCompID, String targetCompID) {
		return new RawFixBuilder(beginString, msgType, senderCompID, targetCompID);
	}
}
