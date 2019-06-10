package org.afc.carril.fix.quickfix.standalone;

import java.util.Arrays;

import org.afc.carril.fix.quickfix.SchemaBaseQuickFixConverter;
import org.afc.carril.fix.quickfix.embedded.EmbeddedQuickFixEngine;
import org.afc.carril.fix.quickfix.listener.TestListener;
import org.afc.carril.fix.quickfix.model.QuoteRequest;
import org.afc.carril.fix.quickfix.model.Symbol;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.Transport;

import org.afc.logging.SDC;
import org.afc.util.JUnitUtil;

public class StandaloneInitiator {

	public static void main(String[] args) {
		JUnitUtil.sleep(3000);
		
		EmbeddedQuickFixEngine initiator = new EmbeddedQuickFixEngine(
			"initiator.yml", 
			"carril.quickfix.initiator", 
			new SchemaBaseQuickFixConverter("src/test/resources/schema/distributor.xml")
		);
		initiator.add("FIX.4.4:BUY-SELL1", new TestListener(), FixMessage.class);
		initiator.start();
		Transport transport = initiator.getTransport();

		while (true) {
		JUnitUtil.sleep(2000);
		SDC.set("QRID1");
		transport.publish("FIX.4.4:BUY-SELL1", new QuoteRequest()
			.setQuoteReqID("QRID-1")
			.setSymbols(Arrays.asList(
				new Symbol().setSymbol("N/A")
				.setSecurityID("N/A")
				.setSecurityIDSource("4")
				.setSecurityDesc("ELN")
				.setEncodedSecurityDescLen(7)
				.setEncodedSecurityDesc("<desc/>")
				.setSide('1')
			))
		);
		JUnitUtil.sleep(8000);
		}
		
	}
}
