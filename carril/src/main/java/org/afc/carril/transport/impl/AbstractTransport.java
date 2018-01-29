package org.afc.carril.transport.impl;

import java.util.LinkedList;
import java.util.List;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.publisher.Publisher;
import org.afc.carril.publisher.PublisherFactory;
import org.afc.carril.subscriber.Subscriber;
import org.afc.carril.subscriber.SubscriberFactory;
import org.afc.carril.transport.ExceptionListener;
import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.SubjectContextFactory;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.Transport;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTransport<C extends SubjectContext> implements Transport, SubscriberFactory, PublisherFactory, SubjectContextFactory<C> {

	protected static Logger logger = LoggerFactory.getLogger(AbstractTransport.class);

	protected State state;

	protected String name;

	protected String subscriberName;

	protected Converter converter;

	protected SubjectRegistry<C> registry;
	
	protected List<ExceptionListener> exceptionListeners;

	public AbstractTransport() {
		this.state = State.DISPOSE;
		this.registry = new DefaultSubjectRegistry<C>();
		this.exceptionListeners = new LinkedList<ExceptionListener>();
	}

	@Override
	public void init() {
		if (state != State.DISPOSE) {
			return;
		}
		logger.info("Transport [{}] initializing.", name);
		doInit();
		state = State.INIT;
		logger.info("Transport [{}] initialized.", name);
	}	
	
	/**
	 * Start the transport
	 * 
	 * @see et.common.Service#start()
	 */
	@Override
	public void start() throws TransportException {
		if (state != State.INIT && state != State.STOP) {
			return;
		}
		logger.info("Transport [{}] starting.", name);

		try {
			for (SubjectContext subject : registry.getSubjectContexts()) {
				for (Subscriber subscription : subject.getSubscribers()) {
					subscription.subscribe();
				}
			}
			doStart();
			state = State.START;
			logger.info("Transport [{}] started.", name);
		} catch (Exception e) {
			throw new TransportException("Transport starting error : ", e);
		}
	}

	@Override
	public void stop() {
		if (state != State.START) {
			return ;
		}
		logger.info("Transport [{}] stopping.", name);
		for (C subjectContext : registry.getSubjectContexts()) {
			for (Subscriber subscription : new LinkedList<Subscriber>(subjectContext.getSubscribers())) {
				try {
					subscription.unsubscribe();
				} catch (Exception e) {
					logger.error("Transport [{}], error on stopping subscriptions stopping.", name);
				}
			}
		}
		doStop();
		registry.unregisterAll();
		state = State.STOP;
		logger.info("Transport [{}] stopped.", name);
	}

	@Override
	public void dispose() {
		if (state != State.STOP && state != State.INIT) {
			return;
		}

		logger.info("Transport [{}] disposing.", name);
	    doDispose();
	    synchronized(exceptionListeners) {
	    	exceptionListeners.clear();
	    }
		state = State.DISPOSE;
		logger.info("Transport [{}] disposed.", name);
	}
	
	@Override
	public State getState() {
	    return state;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getSubscriberID() {
		return subscriberName;
	}

	@Override
	public void setSubscriberID(String subscriberName) {
		this.subscriberName = subscriberName;
	}

	@Override
	public <W, G extends GenericMessage> void setConverter(Converter<W, G> converter) {
		this.converter = converter;
	}
	
	@Override
	public <G extends GenericMessage> Subscriber subscribe(String subject, TransportListener transportListener, Class<G> clazz) throws TransportException {
		return subscribe(subject, transportListener, clazz, converter);
	}

	@Override
	public <W, G extends GenericMessage> Subscriber subscribe(String subject, TransportListener transportListener, Class<G> clazz, Converter<W, G> converter) throws TransportException {
		try {
			C subjectContext = ensureSubjectContext(subject);
			Subscriber subscriber = createSubscriber(subject, transportListener, clazz, converter);
			subjectContext.addSubscriber(subscriber);

			if (state == State.START) {
				subscriber.subscribe();
			}
			return subscriber;
		} catch (TransportException te){
			throw te;
		} catch (Exception e) {
			throw new TransportException("Transport subscription error : ", e);
		}
	}

	@Override
	public void unsubscribe(String subject) throws TransportException {
		SubjectContext subjects = registry.unregister(subject);
		for(Subscriber subscription : subjects.getSubscribers()) {
			subscription.unsubscribe();
		}
	}

	@Override
	public Publisher registerPublisher(String subject) {
		try {
			C subjectContext = ensureSubjectContext(subject);
			Publisher publisher = createPublisher(subject);
			subjectContext.addPublisher(publisher);
			return publisher;
		} catch (TransportException te){
			throw te;
		} catch (Exception e) {
			throw new TransportException("Transport subscription error : ", e);
		}
	}

	@Override
	public <G extends GenericMessage> void publish(String subject, G message) throws TransportException {
		publish(subject, message, converter);
	}

	@Override
	public <W, G extends GenericMessage> void publish(String subject, G message, Converter<W, G> converter) throws TransportException {
		Publisher publisher = ensurePublisher(subject);
		publisher.publish(subject, message, converter);
	}
	
	@Override
	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<G> clazz) throws TransportException {
		return publishRequest(subject, message, clazz, (Converter<W, G>)converter, 0);
	}

	@Override
	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<G> clazz, Converter<W, G> converter) throws TransportException {
		return publishRequest(subject, message, clazz, converter, 0);
	}

	@Override
	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<G> clazz, int timeout) throws TransportException {
		return publishRequest(subject, message, clazz, (Converter<W, G>)converter, timeout);
	}

	@Override
	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<G> clazz, Converter<W, G> converter, int timeout) throws TransportException {
		Publisher publisher = ensurePublisher(subject);
		return publisher.publishRequest(subject, message, clazz, converter, timeout);
	}

	@Override
	public void addExceptionListener(ExceptionListener listener) {
		synchronized(exceptionListeners) {
			exceptionListeners.add(listener);
		}
	}
	
    protected void fireOnException(TransportException e) {
		synchronized(exceptionListeners) {
			for (ExceptionListener listener : exceptionListeners) {
				listener.onException(e);
			}
		}
	}
	
	private C ensureSubjectContext(String subject) {
		C subjectContext = registry.getSubjectContext(subject);
		if (subjectContext == null) {
			subjectContext  = createSubjectContext(subject);
			registry.register(subject, subjectContext);
		}
		return subjectContext;
	}

	private Publisher ensurePublisher(String subject) {
		C subjectContext = ensureSubjectContext(subject);
		Publisher publisher = (subjectContext.getPublishers().size() > 0) ? subjectContext.getPublishers().get(0) : registerPublisher(subject);
		return publisher;
	}
	
	protected abstract void doInit();
	
	protected abstract void doStart();

	protected abstract void doStop();

	protected abstract void doDispose();
}
