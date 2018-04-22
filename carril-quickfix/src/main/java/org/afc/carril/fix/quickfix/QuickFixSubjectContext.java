package org.afc.carril.fix.quickfix;

import org.afc.carril.transport.impl.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.ConfigError;
import quickfix.FieldConvertError;
import quickfix.SessionID;
import quickfix.SessionSettings;

class QuickFixSubjectContext extends DefaultSubjectContext {

	private static final Logger logger = LoggerFactory.getLogger(QuickFixSubjectContext.class);
	
	private SessionID sessionID;
	
	public QuickFixSubjectContext(String subject, SessionSettings sessionSettings) throws ConfigError, FieldConvertError {
		super(subject);
		this.sessionID = QuickFixUtil.createSessionID(sessionSettings, subject);
	}

	public SessionID getSessionID() {
    	return sessionID;
    }
}
