package org.afc.carril.transport;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.publisher.Publisher;
import org.afc.carril.subscriber.Subscriber;

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
	
	public <W, G extends GenericMessage> void setConverter(Converter<W, G> converter);

	public void setName(String name);

	public String getName();

	public void setSubscriberID(String id);

	public String getSubscriberID();

	public <G extends GenericMessage> Subscriber subscribe(String subject, TransportListener listener, Class<G> clazz) throws TransportException;

	public <W, G extends GenericMessage> Subscriber subscribe(String subject, TransportListener listener, Class<G> clazz, Converter<W, G> converter) throws TransportException;

	public void unsubscribe(String subject) throws TransportException;

	public Publisher registerPublisher(String subject);
	
	public <G extends GenericMessage> void publish(String subject, G message) throws TransportException;

	public <W, G extends GenericMessage> void publish(String subject, G message, Converter<W, G> converter) throws TransportException;

	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<G> clazz) throws TransportException;

	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<G> clazz, int timeout) throws TransportException;

	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<G> clazz, Converter<W, G> converter) throws TransportException;

	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<G> clazz, Converter<W, G> converter, int timeout) throws TransportException;

	public Object getBaseImplementation();
	
	public void addExceptionListener(ExceptionListener exceptionListener);
}
