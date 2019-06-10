package org.afc.carril.fix.quickfix;

import org.afc.carril.fix.mapping.fix.FixSubject;
import org.afc.carril.fix.mapping.fix.FixSubjectParser;
import org.afc.carril.fix.tag.FixTag;
import org.afc.carril.message.FixMessage;

import org.afc.logging.SDC;
import org.afc.util.StringUtil;

import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.SessionID;
import quickfix.SessionSettings;

public class QuickFixUtil {

	private static final char D = '\01';
	
	private static final String MSG_SEQ_NUM = "" + D + FixTag.MsgSeqNum.id() + '=';
	
	private static final int MSG_SEQ_NUM_LENGTH = MSG_SEQ_NUM.length();
	
	private static final String CHECK_SUM = "" + D + FixTag.CheckSum.id() + '=';
	
	private static final int CHECK_SUM_LENGTH = CHECK_SUM.length();

	private static final String BEGIN_STRING = FixTag.BeginString.id() + "=";
	
	public static SessionID createSessionID(SessionSettings sessionSettings, String subject) {
		FixSubjectParser parser = FixSubject.newParser(subject);
		return createSessionID(sessionSettings, parser.getBeginString(), parser.getSenderCompID(), parser.getTargetCompID());
	}

	public static SessionID createSessionID(String subject) {
		FixSubjectParser parser = FixSubject.newParser(subject);
		return createSessionID(parser.getBeginString(), parser.getSenderCompID(), parser.getTargetCompID());
	}
	
	private static SessionID createSessionID(String beginString, String senderCompID, String targetCompID) {
		return new SessionID(beginString, senderCompID, targetCompID);
	}
	
	private static SessionID createSessionID(SessionSettings sessionSettings, String beginString, String senderCompID, String targetCompID) {
		SessionID sessionID = createSessionID(beginString, senderCompID, targetCompID);
		sessionSettings.setString(sessionID, SessionSettings.BEGINSTRING, beginString);
		sessionSettings.setString(sessionID, SessionSettings.SENDERCOMPID, senderCompID);
		sessionSettings.setString(sessionID, SessionSettings.TARGETCOMPID, targetCompID);
		// TODO: add session specific settings
		return sessionID;
	}

	public static SessionID createSessionID(Message message) throws FieldNotFound {
		return new SessionID(
			message.getHeader().getString(FixTag.BeginString.id()), 
			message.getHeader().getString(FixTag.SenderCompID.id()), 
			message.getHeader().getString(FixTag.TargetCompID.id()) 
		);
	}

	public static String createSubject(Message message) throws FieldNotFound {
		return FixSubject.newBuilder().
				setBeginString(message.getHeader().getString(FixTag.BeginString.id())).
				setSenderCompID(message.getHeader().getString(FixTag.SenderCompID.id())).
				setTargetCompID(message.getHeader().getString(FixTag.TargetCompID.id())).
				buildSubject();
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
	
	public static final String hash(String message) {
		if (!message.startsWith(BEGIN_STRING)) {
			return SDC.hash(message);
		}
		int p = message.indexOf(MSG_SEQ_NUM);
		String msgSeqNum = message.substring(p + MSG_SEQ_NUM_LENGTH, message.indexOf(D, p + 1));
		p = message.lastIndexOf(CHECK_SUM);
		String checkSum = message.substring(p + CHECK_SUM_LENGTH, message.indexOf(D, p + 1));
		return QuickFixUtil.hash(msgSeqNum, checkSum);
	}

	public static final String hash(String msgSeqNum, String checkSum) {
		return StringUtil.fixLengthInsert('0', String.valueOf((msgSeqNum + checkSum).hashCode()), 5);
	}
	
	public static String toString(Message message) {
		return message.toRawString().replace('\01', '|');
	}
}
