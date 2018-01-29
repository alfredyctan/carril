package org.afc.carril.jms;

import javax.jms.ConnectionFactory;

import org.afc.carril.converter.Converter;
import org.afc.carril.jms.resource.ResourceFactory;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.publisher.Publisher;
import org.afc.carril.subscriber.Subscriber;
import org.afc.carril.transport.TransportListener;
import org.afc.carril.transport.impl.AbstractTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsTransport extends AbstractTransport<JmsSubjectContext> {

	protected static Logger logger = LoggerFactory.getLogger(JmsTransport.class);

	private ResourceFactory factory;

	private String username;

	private String password;

	private String provideUrl;

	private String sslKeyAlias;

	@Override
	protected void doInit() {
	}

	@Override
	protected void doStart() {
	}

	@Override
	protected void doStop() {
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
		return new JmsSubscriber(registry, subject, listener, clazz, converter);
	}

	@Override
	public Publisher createPublisher(String subject) {
		return new JmsPublisher(null, null, registry, subject, null, converter);
	}

	@Override
	public JmsSubjectContext createSubjectContext(String subject) {
		return new JmsSubjectContext(subject, null);
	}
}
