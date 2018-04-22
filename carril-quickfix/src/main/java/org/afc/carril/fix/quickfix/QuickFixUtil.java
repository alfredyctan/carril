package org.afc.carril.fix.quickfix;

import org.afc.carril.fix.mapping.fix.FixSubject;
import org.afc.carril.fix.mapping.fix.FixSubjectParser;
import org.afc.carril.fix.tag.FixTag;
import org.afc.carril.message.FixMessage;

import quickfix.FieldNotFound;
import quickfix.Message;
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
		// TODO: add session specific settings
		return sessionID;
	}

	public static String createOpponentSubject(Message message) throws FieldNotFound {
		return FixSubject.newBuilder().
				setBeginString(message.getHeader().getString(FixTag.BeginString.id())).
				setSenderCompID(message.getHeader().getString(FixTag.TargetCompID.id())).
				setTargetCompID(message.getHeader().getString(FixTag.SenderCompID.id())).
				buildSubject();
	}

	public static FixMessage.Context createContext(SessionID sessionID) {
		return new FixMessage.Context(
			sessionID.getBeginString(),
			sessionID.getSenderCompID(),
			sessionID.getTargetCompID()
		);
	}
}
