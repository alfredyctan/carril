package org.afc.carril.fix.mapping.rawfix;

import org.afc.carril.fix.tag.FixTag;


public class RawFixBuilder extends RawFix {

	private static final int INITIAL_BUFFER = 512;
	
	private StringBuilder valueBuffer;
	
	private String beginString;

	public RawFixBuilder(String beginString, String msgType, String senderCompID, String targetCompID) {
		this.valueBuffer = new StringBuilder(INITIAL_BUFFER);

		this.beginString = beginString;
		append(valueBuffer, FixTag.MsgType.idAsString(), msgType);
		append(valueBuffer, FixTag.SenderCompID.idAsString(), senderCompID);
		append(valueBuffer, FixTag.TargetCompID.idAsString(), targetCompID);
	}

	public RawFix append(String id, String v) {
		append(valueBuffer, id, v);
		return this;
	}	
	
	public void seal() {
		insert(valueBuffer, FixTag.BeginString.idAsString(), beginString);
	}

	private static void append(StringBuilder buffer, String id, String v) {
		buffer.append(id).append(EQ).append(v).append(SOH);
	}	

	private static void insert(StringBuilder buffer, String id, String v) {
		StringBuilder pair = new StringBuilder();
		pair.append(id).append(EQ).append(v).append(SOH);
		buffer.insert(0, pair);
	}	

	@Override
	public String toString() {
		return valueBuffer.toString();
	}


	@Override
    public String findValue(String id) {
	    throw new UnsupportedOperationException("Operation not supported in RawFixBuilder.");
    }


	@Override
    public String findValueFrom(String id, int index) {
	    throw new UnsupportedOperationException("Operation not supported in RawFixBuilder.");
    }


	@Override
    public int getIndex() {
	    throw new UnsupportedOperationException("Operation not supported in RawFixBuilder.");
    }


	@Override
    public void setIndex(int index) {
	    throw new UnsupportedOperationException("Operation not supported in RawFixBuilder.");
    }
}
