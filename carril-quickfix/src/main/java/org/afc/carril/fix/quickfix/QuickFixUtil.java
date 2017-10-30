package org.afc.carril.fix.quickfix;

import org.afc.carril.fix.mapping.fix.FixSubject;
import org.afc.carril.fix.mapping.fix.FixSubjectParser;

import quickfix.SessionID;
import quickfix.SessionSettings;

public class QuickFixUtil {

	public static SessionID createSessionID(SessionSettings sessionSettings, String subject) {
		FixSubjectParser parser = FixSubject.newParser(subject);
		return createSessionID(sessionSettings, parser.getBeginString(), parser.getSenderCompID(), parser.getTargetCompID());
	}
	
	private static SessionID createSessionID(SessionSettings sessionSettings, String beginString, String senderCompID, String targetCompID) {
		SessionID sessionID = new SessionID(beginString, senderCompID, targetCompID);
		sessionSettings.setString(sessionID, SessionSettings.BEGINSTRING, beginString);
		sessionSettings.setString(sessionID, SessionSettings.SENDERCOMPID, senderCompID);
		sessionSettings.setString(sessionID, SessionSettings.TARGETCOMPID, targetCompID);
		return sessionID;
	}


}
