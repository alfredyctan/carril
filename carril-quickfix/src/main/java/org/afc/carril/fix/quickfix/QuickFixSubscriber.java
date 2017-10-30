package org.afc.carril.fix.quickfix;

import java.util.concurrent.ExecutorService;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.FixMessage;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.subscriber.AbstractSubscriber;
import org.afc.carril.subscriber.SubscriberRegistry;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;
import org.afc.util.SDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Message;
import quickfix.SessionID;

class QuickFixSubscriber extends AbstractSubscriber<QuickFixSubjectContext> implements QuickFixMessageHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(QuickFixSubscriber.class);
	
	public QuickFixSubscriber(SubscriberRegistry<QuickFixSubjectContext> registry, String subject, TransportListener transportListener, Class<? extends FixMessage> clazz, Converter<Object, GenericMessage> converter) {
        super(registry, subject, transportListener, clazz, converter);
    }

	@Override
    public void subscribe() throws TransportException {
       	registry.getSubjectContext(subject).addSubscriber(this);
    }

	@Override
    public void unsubscribe() throws TransportException {
		registry.getSubjectContext(subject).removeSubscriber(this);
	}

	@Override
	public void onMessage(final Message message, final SessionID sessionId) {
		final String context = SDC.peek();
		class OnMessageRunnable implements Runnable {

			public void run() {
				SDC.set(context);
				try {
					// Convert to generic format in adaptor
					GenericMessage convertedData = converter.parse(message, clazz);

					// process the message
					if (convertedData == null) {
						logger.trace("cannot convert any message to handle : {}", subject);
						return;
					}
					transportListener.onMessage(convertedData);
					
					//Fix is async-only protocal
					logger.trace("message handled : {}", subject);
				} catch (Exception e) {
					logger.error("Exception occurs, reprocess is required.", e);
				}
			} // /run()
		}

		if (transportListener == null) { // for the case that publish without create session
			return;
		}
		ExecutorService executorService = transportListener.getExecutorService();
		if (executorService != null) {
			logger.trace("{} using provided executor service to handle", subject);
			executorService.execute(new OnMessageRunnable());
		} else {
			logger.trace("{} using transport thread to handle", subject);
			new OnMessageRunnable().run();
		}
	}
}
