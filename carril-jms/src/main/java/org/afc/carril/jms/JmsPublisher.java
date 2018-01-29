package org.afc.carril.jms;

import java.util.concurrent.Semaphore;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.afc.carril.converter.Converter;
import org.afc.carril.message.GenericMessage;
import org.afc.carril.publisher.AbstractPublisher;
import org.afc.carril.transport.SubjectRegistry;
import org.afc.carril.transport.TransportException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JmsPublisher extends AbstractPublisher<JmsSubjectContext> {

	protected static final Logger logger = LoggerFactory.getLogger(JmsPublisher.class);

	private Semaphore lock;

	private Session session;

	private MessageProducer producer;

	protected <W, G extends GenericMessage> JmsPublisher(MessageProducer producer, Session session,
			SubjectRegistry<JmsSubjectContext> registry, String subject, Class<G> clazz, Converter<W, G> converter) {
		super(registry, subject, clazz, converter);
		this.session = session;
		this.producer = producer;
	}

	@Override
	public <W, G extends GenericMessage> void publish(String subject, G message, Converter<W, G> converter) throws TransportException {
		try {
			lock.acquire();
			Message jmsMessage = (Message)converter.format(message);
			producer.send(jmsMessage);
			logger.debug("The message has been published successfully. topic : {}", subject);
		} catch (Exception e) {
			throw new TransportException("Fail to send the message to " + subject, e);
		} finally {
			lock.release();
		}
	}

	@Override
	public <W, G extends GenericMessage> G publishRequest(String subject, G message, Class<? extends G> clazz, Converter<W, G> converter, int timeout) throws TransportException {
		try {
			lock.acquire();
			Message jmsMessage = (Message)converter.format(message);
			producer.send(jmsMessage);
			logger.debug("The message has been published successfully. topic : {}", subject);
		} catch (Exception e) {
			throw new TransportException("Fail to send the message to " + subject, e);
		} finally {
			lock.release();
		}
		return null;
	}
	
	@Override
	public void dispose() {
		try {
			producer.close();
		} catch (JMSException e) {
			logger.warn("publisher is not closed gracefully", e);
		}
	}
}
