package org.afc.carril.fix.quickfix;

import java.util.Map;
import java.util.TreeMap;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.publisher.Publisher;
import org.afc.carril.subscriber.Subscriber;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;
import org.afc.carril.transport.impl.AbstractTransport;
import org.afc.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.ConfigError;
import quickfix.SessionSettings;

public class QuickFixTransport extends AbstractTransport<QuickFixSubjectContext> {

	private static final Logger logger = LoggerFactory.getLogger(QuickFixTransport.class);
	
	private QuickFixSettings settings;
	
	public QuickFixTransport() {
		this.settings = new QuickFixSettings();
	}
	
	@Override
	protected void doInit() {
		SessionSettings sessionSettings = settings.createDefaultSessionSettings();
    	logger.info("default session properties : ");
	    for (Map.Entry<?, ?> entry : new TreeMap<Object, Object>(sessionSettings.getDefaultProperties()).entrySet()) {
	    	Object key = (String)entry.getKey();
	    	Object value = (String)entry.getValue();
	    	logger.info("{} = {}", key, value);
	    }

	}

	@Override
	protected void doStart() {
		for (QuickFixSubjectContext subjectContext : registry.getSubjectContexts()) {
			try {
	            subjectContext.start();
            } catch (Exception e) {
            	throw new TransportException(e);
            }
		}
	}

	@Override
	protected void doStop() {
		for (QuickFixSubjectContext subjectContext : registry.getSubjectContexts()) {
			try {
				subjectContext.stop();
            } catch (Exception e) {
            	logger.error("Failed to stop the QuickFix Transport", e);
            }
		}
	}

	@Override
	protected void doDispose() {
	}

	@Override
	public Object getBaseImplementation() {
		return null;
	}

	@Override
	public <W, G extends GenericMessage> Subscriber createSubscriber(String subject, TransportListener listener, Class<G> clazz, Converter<W, G> converter) {
    	return new QuickFixSubscriber(registry, subject, listener, ObjectUtil.<Class<G>>cast(clazz), converter);
	}
	
//    @Override
//	public Subscriber createSubscriber(String subject, TransportListener transportListener, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter) {
//    	return new QuickFixSubscriber(registry, subject, transportListener, ObjectUtil.<Class<? extends FixMessage>>cast(clazz), converter);
//	}

	@Override
	public Publisher createPublisher(String subject) {
		return new QuickFixPublisher(registry, subject, null, converter);
	}

    @Override
    public QuickFixSubjectContext createSubjectContext(String subject) {
        try {
	        return new QuickFixSubjectContext(subject, settings.createDefaultSessionSettings(), settings.getFileStorePath(), state) ;
        } catch (ConfigError e) {
        	throw new TransportException("fail to SubjectContext.", e);
        }
    }

	public void setSettings(QuickFixSettings settings) {
    	this.settings = settings;
    }

}
