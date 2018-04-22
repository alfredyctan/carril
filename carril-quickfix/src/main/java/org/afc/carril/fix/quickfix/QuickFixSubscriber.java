package org.afc.carril.fix.quickfix;

import java.util.concurrent.ExecutorService;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.subscriber.AbstractSubscriber;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportListener;
import org.afc.logging.SDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Message;

public class QuickFixSubscriber extends AbstractSubscriber<QuickFixSubjectContext> implements QuickFixMessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(QuickFixSubscriber.class);
	
	public <W, G extends GenericMessage> QuickFixSubscriber(SubjectRegistry<QuickFixSubjectContext> registry, String subject, TransportListener<GenericMessage> listner, Class<GenericMessage> clazz, Converter<W, G> converter) {
        super(registry, subject, listner, clazz, converter);
    }

	@Override
	public void onMessage(final Message message) {
		final String context = SDC.peek();
		class OnMessageRunnable implements Runnable {

			public void run() {
				SDC.set(context);
				try {
					// Convert to generic format in adaptor
					@SuppressWarnings("unchecked")
					GenericMessage convertedData = converter.parse(message, clazz);

					// process the message
					if (convertedData == null) {
						logger.trace("cannot convert any message to handle : {}", subject);
						return;
					}
					listener.onMessage(convertedData);
					
					//Fix is async-only protocal
					logger.trace("message handled : {}", subject);
				} catch (Exception e) {
					logger.error("Exception occurs, reprocess is required.", e);
				}
			} // /run()
		}

		if (listener == null) { // for the case that publish without create session
			return;
		}
		ExecutorService executorService = listener.getExecutorService();
		if (executorService != null) {
			logger.trace("{} using provided executor service to handle", subject);
			executorService.execute(new OnMessageRunnable());
		} else {
			logger.trace("{} using transport thread to handle", subject);
			new OnMessageRunnable().run();
		}
	}
}
