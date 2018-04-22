package org.afc.carril.fix.quickfix;

import quickfix.Message;

interface QuickFixMessageListener {
	
	public void onMessage(Message message);
	
}