package org.afc.carril.fix.quickfix.listener;

import org.afc.carril.fix.quickfix.model.Logon;
import org.afc.carril.fix.quickfix.model.Quote;
import org.afc.carril.fix.quickfix.model.QuoteRequest;
import org.afc.carril.fix.quickfix.model.Symbol;
import org.afc.carril.message.FixMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestListener extends StashListener<FixMessage> {
	
	private static final Logger logger = LoggerFactory.getLogger(TestListener.class);

	@Override
	public FixMessage onMessage(FixMessage message) {
		super.onMessage(message);
		if (message instanceof Logon) {
			logger.info("logon received {}", message);
		} else if (message instanceof QuoteRequest) {
			logger.info("quote request received {}", message);
			QuoteRequest quoteRequest = (QuoteRequest)message;
			Symbol symbol = quoteRequest.getSymbols().get(0);
			return new Quote()
				.<Quote>with(quoteRequest.getContext().reverse())
				.setQuoteReqID(quoteRequest.getQuoteReqID())
				.setQuoteID("100")
				.setSymbol(symbol.getSymbol())
				.setSecurityID(symbol.getSecurityID())
				.setSecurityIDSource(symbol.getSecurityIDSource())
				.setEncodedSecurityDescLen(symbol.getEncodedSecurityDescLen())
				.setEncodedSecurityDesc(symbol.getEncodedSecurityDesc())
				.setSide(symbol.getSide());
		} else {
			logger.info("message received {}", message);
		}
		return null;
	}
}
