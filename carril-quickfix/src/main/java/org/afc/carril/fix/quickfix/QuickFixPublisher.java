package org.afc.carril.fix.quickfix;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.FixMessage;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.publisher.AbstractPublisher;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;
import org.afc.util.ObjectUtil;

import quickfix.Message;
import quickfix.Session;
import quickfix.SessionNotFound;

public class QuickFixPublisher extends AbstractPublisher<QuickFixSubjectContext> {

	protected <W, G extends GenericMessage> QuickFixPublisher(SubjectRegistry<QuickFixSubjectContext> registry, String subject,
			Class<G> clazz,
			Converter<W, G> converter) {
		super(registry, subject, clazz, converter);
	}

	@Override
	public <W, G extends GenericMessage> void publish(String subject, G message, Converter<W, G> converter) throws TransportException {
		try {
			QuickFixSubjectContext application = registry.getSubjectContext(subject);
			FixMessage fixFormat = ObjectUtil.<FixMessage>cast(message);
	        fixFormat.setContext(
	        	new FixMessage.Context(
		        	application.getSessionID().getBeginString(),
		        	application.getSessionID().getSenderCompID(),
		        	application.getSessionID().getTargetCompID()
		        )
	        );
			Message jmsMessage = (Message)converter.format(message);
	        Session.sendToTarget(jmsMessage, application.getSessionID());
		} catch (SessionNotFound e) {
        	throw new TransportException("fail to send message. ", e);
        }
	}

	@Override
	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<? extends G> clazz,
			Converter<W, G> converter, int timeout) throws TransportException {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
}