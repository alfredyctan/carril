package org.afc.carril.fix.quickfix.standalone.send;

import org.afc.carril.fix.quickfix.QuickFixUtil;

import quickfix.FieldNotFound;
import quickfix.Message;
import quickfix.Session;
import quickfix.SessionNotFound;

public interface SessionSender {
	
	default public void send(Message fixMessage) throws SessionNotFound, FieldNotFound {
		Session.sendToTarget(fixMessage, QuickFixUtil.createSessionID(fixMessage));
	}
}
