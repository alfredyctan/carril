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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.afc.util.ObjectUtil;

import quickfix.ConfigError;
import quickfix.Connector;
import quickfix.DefaultMessageFactory;
import quickfix.DefaultSessionFactory;
import quickfix.FieldConvertError;
import quickfix.FileStoreFactory;
import quickfix.MemoryStoreFactory;
import quickfix.RuntimeError;
import quickfix.SessionFactory;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;
import quickfix.SocketInitiator;
/*
 * 1 transport - 1 connector type - (n subject / n session), 1 subject - n subscriber callback 
 */
public class QuickFixTransport extends AbstractTransport<QuickFixSubjectContext> {

	private static final Logger logger = LoggerFactory.getLogger(QuickFixTransport.class);
	
	private QuickFixSettings settings;

	private SessionSettings sessionSettings;

	private SessionFactory sessionFactory; 

	private Connector connector; 

	@Override
	protected void doInit() {
		sessionSettings = settings.createDefaultSessionSettings();
    	
		logger.info("default session properties : ");
	    for (Map.Entry<?, ?> entry : new TreeMap<Object, Object>(sessionSettings.getDefaultProperties()).entrySet()) {
	    	Object key = (String)entry.getKey();
	    	Object value = (String)entry.getValue();
	    	logger.info("{} = {}", key, value);
	    }

		sessionFactory = new DefaultSessionFactory(
			new QuickFixApplication(registry), 
			(settings.getFileStorePath() == null) ? new MemoryStoreFactory() : new FileStoreFactory(sessionSettings), 
			new CarrilSLF4JLogFactory(sessionSettings), 
			new DefaultMessageFactory()
		);
	    
		try {
			switch (settings.getConnectionType()) {
				case SessionFactory.INITIATOR_CONNECTION_TYPE:
					connector = new SocketInitiator(sessionFactory, sessionSettings, 1024);
					break;
				case SessionFactory.ACCEPTOR_CONNECTION_TYPE:
					connector = new SocketAcceptor(sessionFactory, sessionSettings, 1024);
					break;
				default:
					throw new TransportException("invalid connection type " + settings.getConnectionType());
			}
		} catch (ConfigError e) {
			throw new TransportException("fail to create connector", e);
		}
	}

	@Override
	protected void doStart() {
		try {
			connector.start();
			logger.debug("connector started.");
		} catch (RuntimeError | ConfigError e) {
			throw new TransportException("fail to start connector", e);
		}
	}

	@Override
	protected void doStop() {
		connector.stop(true);
		logger.debug("connector stopped.");
	}

	@Override
	protected void doDispose() {
	}

	@Override
	public Object getBaseImplementation() {
		return new Object[] {sessionFactory, connector};
	}

	@Override
	public <W, G extends GenericMessage> Subscriber createSubscriber(String subject, TransportListener<GenericMessage> listener, Class<G> clazz, Converter<W, G> converter) {
    	return new QuickFixSubscriber(registry, subject, listener, ObjectUtil.<Class<GenericMessage>>cast(clazz), converter);
	}
	
	@Override
	public Publisher createPublisher(String subject) {
		return new QuickFixPublisher(registry, subject, converter);
	}

    @Override
    public QuickFixSubjectContext createSubjectContext(String subject) {
        try {
	        return new QuickFixSubjectContext(subject, sessionSettings) ;
        } catch (ConfigError | FieldConvertError e) {
        	throw new TransportException("fail to SubjectContext.", e);
        }
    }

	public void setSettings(QuickFixSettings settings) {
    	this.settings = settings;
    }

}
