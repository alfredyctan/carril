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

	protected QuickFixPublisher(SubjectRegistry<QuickFixSubjectContext> registry, String subject,
			TransportListener transportListener, Class<? extends GenericMessage> clazz,
			Converter<Object, GenericMessage> converter) {
		super(registry, subject, transportListener, clazz, converter);
	}

	@Override
	public void publish(String subject, GenericMessage fmtObj, Converter<Object, GenericMessage> converter) throws TransportException {
		try {
			QuickFixSubjectContext application = registry.getSubjectContext(subject);
			FixMessage fixFormat = ObjectUtil.<FixMessage>cast(fmtObj);
	        fixFormat.setContext(
	        	new FixMessage.Context(
		        	application.getSessionID().getBeginString(),
		        	application.getSessionID().getSenderCompID(),
		        	application.getSessionID().getTargetCompID()
		        )
	        );
			Message message = (Message)converter.format(fmtObj);
	        Session.sendToTarget(message, application.getSessionID());
		} catch (SessionNotFound e) {
        	throw new TransportException("fail to send message. ", e);
        }
	}
		
	@Override
	public <G extends GenericMessage> G publishRequest(String subject, GenericMessage message,
			Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter, int timeout)
			throws TransportException {
		publish(subject, message, converter);
		return null;
	}
}
