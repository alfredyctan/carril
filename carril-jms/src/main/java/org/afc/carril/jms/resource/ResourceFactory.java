package org.afc.carril.jms.resource;

import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Session;

import org.afc.carril.transport.TransportException;

public interface ResourceFactory {

	public ConnectionFactory createConnectionFactory(String providerUrl) throws TransportException;

	public Connection createConnection(String username, String password) throws TransportException;
	
	public Session createSession(Connection connection) throws TransportException;
	
	public MessageConsumer createMessageConsumer(String subject, Map<String, String> config) throws TransportException;
	
	public MessageProducer createMessageProducer(String subject, Map<String, String> config) throws TransportException;

}
