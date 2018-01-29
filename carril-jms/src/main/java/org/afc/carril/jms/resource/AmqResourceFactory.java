package org.afc.carril.jms.resource;

import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.afc.carril.transport.TransportException;
import org.apache.activemq.ActiveMQConnectionFactory;

public class AmqResourceFactory implements ResourceFactory {

	private ConnectionFactory connectionFactory;

	private Connection connection;

	@Override
	public ConnectionFactory createConnectionFactory(String providerUrl) throws TransportException {
		if (connectionFactory == null) {
			this.connectionFactory = new ActiveMQConnectionFactory(providerUrl);
		}
		return connectionFactory;
	}

	@Override
	public Connection createConnection(String username, String password) throws TransportException {
		try {
			if (connection == null) {
				if (username == null || password == null) {
					connection = connectionFactory.createConnection();
				} else {
					connection = connectionFactory.createConnection(username, password);
				}
			}
			return connection;
		} catch (JMSException e) {
			throw new TransportException("failed to creation connection", e);
		}
	}

	@Override
	public Session createSession(Connection connection) throws TransportException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageConsumer createMessageConsumer(String subject, Map<String, String> config) throws TransportException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MessageProducer createMessageProducer(String subject, Map<String, String> config) throws TransportException {
		// TODO Auto-generated method stub
		return null;
	}

}
