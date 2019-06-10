package org.afc.carril.fix.quickfix;

import org.afc.logging.SDC;

import quickfix.Log;

public class CarrilSLF4JLog implements Log {

	private Log log;

	public CarrilSLF4JLog(Log log) {
		this.log = log;
	}

	@Override
	public void clear() {
		SDC.auto();
		log.clear();
	}

	@Override
	public void onEvent(String text) {
		SDC.auto(text);
		log.onEvent(text);
		SDC.remove();
	}

	@Override
	public void onErrorEvent(String text) {
		SDC.auto(text);
		log.onErrorEvent(text);
		SDC.remove();
	}

	@Override
	public void onIncoming(String message) {
		SDC.set((Object)message);
		log.onIncoming(message);
	}
	@Override
	public void onOutgoing(String message) {
		SDC.auto(message);
		log.onOutgoing(message);
	}
}