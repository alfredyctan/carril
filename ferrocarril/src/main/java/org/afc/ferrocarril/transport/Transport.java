package org.afc.ferrocarril.transport;

import org.afc.ferrocarril.converter.Converter;
import org.afc.ferrocarril.message.GenericMessage;
import org.afc.ferrocarril.subscriber.Subscriber;

public interface Transport{

	public enum State {
		INIT,
		START,
		STOP,
		DISPOSE;
	}
	
	public void init();

	public void start();

	public void stop();

	public void dispose();

	public State getState();
	
	public void setConverter(Converter<Object, GenericMessage> converter);

	public void setName(String name);

	public String getName();

	public void setSubscriberID(String id);

	public String getSubscriberID();

	public Subscriber subscribe(String subject, TransportListener listener, Class<? extends GenericMessage> clazz) throws TransportException;

	public Subscriber subscribe(String subject, TransportListener listener, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter) throws TransportException;

	public void unsubscribe(String subject) throws TransportException;

	public void publish(String subject, GenericMessage message) throws TransportException;

	public void publish(String subject, GenericMessage message, Converter<Object, GenericMessage> converter) throws TransportException;

	public <G extends GenericMessage> G publishRequest(String subject, GenericMessage message, Class<? extends GenericMessage> clazz) throws TransportException;

	public <G extends GenericMessage> G publishRequest(String subject, GenericMessage message, Class<? extends GenericMessage> clazz, int timeout) throws TransportException;

	public <G extends GenericMessage> G publishRequest(String subject, GenericMessage message, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter) throws TransportException;

	public <G extends GenericMessage> G publishRequest(String subject, GenericMessage message, Class<? extends GenericMessage> clazz, Converter<Object, GenericMessage> converter, int timeout) throws TransportException;

	public Object getBaseImplementation();
	
	public void addExceptionListener(ExceptionListener listener);

}
