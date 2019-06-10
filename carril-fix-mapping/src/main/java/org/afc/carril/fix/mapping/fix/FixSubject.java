package org.afc.carril.fix.mapping.fix;

import org.afc.carril.message.FixMessage.Context;

public class FixSubject implements BeginStringSetter, SenderCompIDSetter, TargetCompIDSetter, FixSubjectBuilder, FixSubjectParser {

	private String beginString;

	private String senderCompID;

	private String targetCompID;

	private FixSubject() {
	}

	public static BeginStringSetter newBuilder() {
		return new FixSubject();
	}

	public static FixSubjectParser newParser(String subject) {
		FixSubject fixSubject = new FixSubject();
		fixSubject.parseSubject(subject);
		return fixSubject;
	}
	
	@Override
	public SenderCompIDSetter setBeginString(String beginString) {
		this.beginString = beginString;
		return this;
	}

	@Override
	public TargetCompIDSetter setSenderCompID(String senderCompID) {
		this.senderCompID = senderCompID;
		return this;
	}

	@Override
	public FixSubject setTargetCompID(String targetCompID) {
		this.targetCompID = targetCompID;
		return this;
	}

	@Override
	public String buildSubject() {
		return beginString + ':' + senderCompID + '-' + targetCompID;
	}

	@Override
	public String getBeginString() {
    	return beginString;
    }

	@Override
	public String getSenderCompID() {
    	return senderCompID;
    }

	@Override
	public String getTargetCompID() {
    	return targetCompID;
    }
	
	@Override
	public String getSessionID() {
	    return senderCompID + '-' + targetCompID;
	}
	
	@Override
	public Context toContext() {
		return new Context(beginString, senderCompID, targetCompID);
	}

	private void parseSubject(String subject) {
		String[] begin = subject.split(":");
		if (begin.length != 2) {
			throw new RuntimeException("Missing BeginString delimiter ':', unable to retrieve BeginString from subject " + subject);
		}
		
		String[] compID = begin[1].split("-");
		if (compID.length != 2) {
			throw new RuntimeException("Missing CompID delimiter '-', unable to retrieve CompID from subject " + subject);
		}
		beginString = begin[0];
		senderCompID = compID[0];
		targetCompID = compID[1];
	}
}
