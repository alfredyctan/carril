package org.afc.carril.fix.quickfix.listener;

import java.util.LinkedList;
import java.util.List;

import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.TransportListener;

public class StashListener<G extends GenericMessage> implements TransportListener<G> {
	
	private List<G> messages;
	
	public StashListener() {
		this.messages = new LinkedList<>();
	}

	@Override
	public G onMessage(G message) {
		messages.add(message);
		return null;
	}

	public List<G> getMessages() {
		return messages;
	}
}
