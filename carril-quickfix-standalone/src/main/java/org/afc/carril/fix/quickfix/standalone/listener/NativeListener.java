package org.afc.carril.fix.quickfix.standalone.listener;

import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.afc.carril.fix.quickfix.message.Crude;
import org.afc.carril.transport.TransportListener;

public class NativeListener implements TransportListener<Crude> {
	
	private static final Logger logger = LoggerFactory.getLogger(NativeListener.class);

	private List<Crude> messages;
	
	public NativeListener() {
		this.messages = new LinkedList<>();
	}

	@Override
	public Crude onMessage(Crude message) {
		messages.add(message);
		logger.info("on message : {}", message);
		return null;
	}

	public List<Crude> getMessages() {
		return messages;
	}
}
