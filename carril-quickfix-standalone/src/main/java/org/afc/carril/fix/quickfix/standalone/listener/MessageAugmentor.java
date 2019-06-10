package org.afc.carril.fix.quickfix.standalone.listener;

import quickfix.Message;

public interface MessageAugmentor {

	public void augment(Message message);
	
}
