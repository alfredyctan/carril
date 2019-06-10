package org.afc.carril.fix.quickfix;

import java.util.concurrent.ExecutorService;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.subscriber.AbstractSubscriber;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.afc.logging.SDC;
import org.afc.util.StopWatch;

import quickfix.Message;
import quickfix.Session;

public class QuickFixSubscriber extends AbstractSubscriber<QuickFixSubjectContext> implements QuickFixMessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(QuickFixSubscriber.class);
	
	private QuickFixSubjectContext subjectContext;

	public <W, G extends GenericMessage> QuickFixSubscriber(SubjectRegistry<QuickFixSubjectContext> registry, String subject, TransportListener<GenericMessage> listner, Class<GenericMessage> clazz, Converter<W, G> converter) {
        super(registry, subject, listner, clazz, converter);
		subjectContext = registry.getSubjectContext(subject);
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
					StopWatch watch = new StopWatch(4);
					watch.tick();
					GenericMessage convertedData = converter.parse(message, clazz);
					watch.tick();

					// process the message
					if (convertedData == null) {
						logger.debug("no converted data to process, parse(ms):[{}]", watch.millis(0));
						return;
					}

					GenericMessage processed = listener.onMessage(convertedData);
					watch.tick();
					if (processed == null) {
						logger.debug("no auto response, parse(ms):[{}], process(ms):[{}]", watch.millis(0), watch.millis(1));
					} else {
						Message response = (Message)converter.format(processed);
						watch.tick();
				        Session.sendToTarget(response, subjectContext.getSessionID());
						logger.debug("auto response, parse(ms):[{}], process(ms):[{}], format(ms):[{}]", watch.millis(0), watch.millis(1), watch.millis(2));
					}
					
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
