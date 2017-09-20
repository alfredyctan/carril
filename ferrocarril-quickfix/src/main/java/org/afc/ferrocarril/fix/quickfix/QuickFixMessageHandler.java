package org.afc.ferrocarril.fix.quickfix;

import quickfix.Message;
import quickfix.SessionID;

interface QuickFixMessageHandler {
	
	public void onMessage(Message message, SessionID sessionId);
	
}