package org.afc.carril.fix.quickfix.standalone;

import org.afc.carril.fix.quickfix.SchemaBaseQuickFixConverter;
import org.afc.carril.fix.quickfix.embedded.EmbeddedQuickFixEngine;
import org.afc.carril.fix.quickfix.listener.TestListener;
import org.afc.carril.message.FixMessage;

public class StandaloneAcceptor {

	public static void main(String[] args) {
		EmbeddedQuickFixEngine acceptor = new EmbeddedQuickFixEngine(
			"acceptor.yml", 
			"carril.quickfix.acceptor", 
			new SchemaBaseQuickFixConverter("src/test/resources/schema/acceptor.xml")
		);
		acceptor.add("FIX.4.4:SELL1-BUY", new TestListener(), FixMessage.class);
		acceptor.add("FIX.4.4:SELL2-BUY", new TestListener(), FixMessage.class);
		acceptor.start();		
	}
}
