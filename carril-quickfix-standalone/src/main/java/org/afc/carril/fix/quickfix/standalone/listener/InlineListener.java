package org.afc.carril.fix.quickfix.standalone.listener;

import java.util.List;

import org.afc.carril.message.GenericMessage;
import org.afc.carril.transport.TransportListener;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InlineListener<T extends GenericMessage> implements TransportListener<T>{
	
	private List<TransportListener<T>> listeners;
	
	@Override
	public T onMessage(T message) {
		for (TransportListener<T> listener : listeners) {
			T response = listener.onMessage(message);
			if (response != null) {
				return response;
			}
		}
		return null;
	}
}
