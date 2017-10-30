package org.afc.carril.transport.impl;

import java.util.LinkedList;
import java.util.List;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.subscriber.DefaultSubscriberRegistry;
import org.afc.carril.subscriber.Subscriber;
import org.afc.carril.subscriber.SubscriberFactory;
import org.afc.carril.subscriber.SubscriberRegistry;
import org.afc.carril.transport.ExceptionListener;
import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.SubjectContextFactory;
import org.afc.carril.transport.Transport;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.TransportListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractTransport<C extends SubjectContext> implements Transport, SubscriberFactory, SubjectContextFactory<C> {

	protected static Logger logger = LoggerFactory.getLogger(AbstractTransport.class);

	protected State state;

	protected String name;

	protected String subscriberName;

	protected Converter<Object, GenericMessage> converter;

	protected SubscriberRegistry<C> registry;
	
	protected List<ExceptionListener> exptListeners;

	public AbstractTransport() {
		this.state = State.DISPOSE;
		this.registry = new DefaultSubscriberRegistry<C>();
		this.exptListeners = new LinkedList<ExceptionListener>();
		this.registry.setSubjectContextFactory(this);
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
			for (SubjectContext subject : registry.allSubjectContexts()) {
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
		for (C subjectContext : registry.allSubjectContexts()) {
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
	    synchronized(exptListeners) {
	    	exptListeners.clear();
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
	public void setConverter(Converter<Object, GenericMessage> converter) {
		this.converter = converter;
	}
	
	@Override
	public void publish(String subject, GenericMessage fmtObj) throws TransportException {
		publish(subject, fmtObj, converter);
	}

	@Override
	public <G extends GenericMessage> G publishRequest(String subject, GenericMessage fmtObj, Class<? extends GenericMessage> clazz) throws TransportException {
		return publishRequest(subject, fmtObj, clazz, converter);
	}

	@Override
	public <G extends GenericMessage> G publishRequest(String subject, GenericMessage fmtObj, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter) throws TransportException {
		return publishRequest(subject, fmtObj, clazz, converter, 0);
	}

	@Override
	public <G extends GenericMessage> G publishRequest(String subject, GenericMessage fmtObj, Class<? extends GenericMessage> clazz, int timeout) throws TransportException {
		return publishRequest(subject, fmtObj, clazz, converter, timeout);
	}

	@Override
	public Subscriber subscribe(String subject, TransportListener transportListener, Class<? extends GenericMessage> clazz) throws TransportException {
		return subscribe(subject, transportListener, clazz, converter);
	}

	@Override
	public Subscriber subscribe(String subject, TransportListener transportListener, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter) throws TransportException {
		try {
			Subscriber subscription = createSubscriber(subject, transportListener, clazz, converter);
			registry.register(subject, subscription);

			if (state == State.START) {
				subscription.subscribe();
			}
			return subscription;
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
	public void addExceptionListener(ExceptionListener listener) {
		synchronized(exptListeners) {
			exptListeners.add(listener);
		}
	}
	
    protected void fireOnException(TransportException e) {
		synchronized(exptListeners) {
			for (ExceptionListener listener : exptListeners) {
				listener.onException(e);
			}
		}
	}
	
	protected abstract void doInit();
	
	protected abstract void doStart();

	protected abstract void doStop();

	protected abstract void doDispose();
}
