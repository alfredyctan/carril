package org.afc.carril.fix.quickfix.standalone.send;

import java.util.function.Consumer;

import org.afc.carril.fix.quickfix.standalone.repository.MessageRepository;

import quickfix.Message;

public interface RepositorySender {

	public void send(MessageRepository repository, Message message, Runnable complete, Consumer<Exception> listener);
	
}
