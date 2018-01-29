package org.afc.carril.jms;

import javax.jms.Session;

import org.afc.carril.transport.impl.DefaultSubjectContext;

public class JmsSubjectContext extends DefaultSubjectContext {

	private Session session;

	public JmsSubjectContext(String subject, Session session) {
		super(subject);
		this.session = session;
	}

	public Session getSession() {
		return session;
	}
}
