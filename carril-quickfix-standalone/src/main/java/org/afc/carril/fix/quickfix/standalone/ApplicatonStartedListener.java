package org.afc.carril.fix.quickfix.standalone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

import org.afc.carril.fix.quickfix.embedded.EmbeddedQuickFixEngine;

public class ApplicatonStartedListener implements ApplicationListener<ApplicationStartedEvent> {
	
	@Autowired
	private EmbeddedQuickFixEngine engine;
	
	@Override
	public void onApplicationEvent(ApplicationStartedEvent event) {
		engine.start();
	}

}
