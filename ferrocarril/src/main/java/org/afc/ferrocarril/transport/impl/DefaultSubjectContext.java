package org.afc.ferrocarril.transport.impl;

import java.util.ArrayList;
import java.util.List;

import org.afc.ferrocarril.subscriber.Subscriber;
import org.afc.ferrocarril.transport.SubjectContext;

public class DefaultSubjectContext implements SubjectContext {

	protected List<Subscriber> subscriptions;

	protected String subject;

	public DefaultSubjectContext(String subject) {
		this.subject = subject;
		this.subscriptions = new ArrayList<Subscriber>();
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public List<Subscriber> getSubscribers() {
		return subscriptions;
	}

	@Override
	public void addSubscriber(Subscriber subscription) {
		synchronized(subscriptions) {
			if (!subscriptions.contains(subscription)) {
				subscriptions.add(subscription);
			}
		}
	}

	@Override
	public void removeSubscriber(Subscriber subscription) {
		synchronized(subscriptions) {
			subscriptions.remove(subscription);
		}
	}
}
