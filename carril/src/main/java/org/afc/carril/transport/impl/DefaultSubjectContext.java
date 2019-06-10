package org.afc.carril.transport.impl;

import java.util.ArrayList;
import java.util.List;

import org.afc.carril.publisher.Publisher;
import org.afc.carril.subscriber.Subscriber;
import org.afc.carril.transport.SubjectContext;

import lombok.ToString;

@ToString
public class DefaultSubjectContext implements SubjectContext {

	protected String subject;

	protected List<Subscriber> subscribers;

	protected List<Publisher> publishers;

	public DefaultSubjectContext(String subject) {
		this.subject = subject;
		this.subscribers = new ArrayList<>();
		this.publishers = new ArrayList<>();
	}

	@Override
	public String getSubject() {
		return subject;
	}

	@Override
	public List<Subscriber> getSubscribers() {
		return subscribers;
	}

	@Override
	public void addSubscriber(Subscriber subscriber) {
		synchronized(subscribers) {
			if (!subscribers.contains(subscriber)) {
				subscribers.add(subscriber);
			}
		}
	}

	@Override
	public void removeSubscriber(Subscriber subscriber) {
		synchronized(subscribers) {
			subscribers.remove(subscriber);
		}
	}

	@Override
	public List<Publisher> getPublishers() {
		return publishers;
	}

	@Override
	public void addPublisher(Publisher publisher) {
		synchronized(publishers) {
			if (!publishers.contains(publisher)) {
				publishers.add(publisher);
			}
		}
	}

	@Override
	public void removePublisher(Publisher publisher) {
		synchronized(publishers) {
			publishers.remove(publisher);
		}
	}
}
