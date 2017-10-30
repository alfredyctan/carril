package org.afc.carril.subscriber;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.SubjectContextFactory;

public class DefaultSubscriberRegistry<C extends SubjectContext> implements SubscriberRegistry<C> {

	private Map<String, C> subjectContexts;

	private SubjectContextFactory<C> factory;

	public DefaultSubscriberRegistry() {
		this.subjectContexts = new ConcurrentHashMap<String, C>();
	}

	@Override
	public Collection<C> allSubjectContexts() {
		return subjectContexts.values();
	}

	@Override
	public Map<String, C> unregisterAll() {
		Map<String, C> unregistered = subjectContexts;
		subjectContexts = new ConcurrentHashMap<String, C>();
		return unregistered;
	}

	@Override
	public void register(String subject, Subscriber subscription) {
		C subjectContext = getSubjectContext(subject);
		subjectContext.addSubscriber(subscription);
	}

	@Override
	public void register(String subject, C subjectContext) {
		synchronized(subjectContexts) {
			subjectContexts.put(subject, subjectContext);
		}
	}

	@Override
	public C unregister(String subject) {
		synchronized(subjectContexts) {
			C subjectContext = subjectContexts.remove(subject);
			return (subjectContext == null) ? factory.createSubjectContext(subject) : subjectContext;
		}
	}

	@Override
	public C getSubjectContext(String subject) {
		synchronized(subjectContexts) {
			C subjectContext = subjectContexts.get(subject);
			if (subjectContext == null) {
				subjectContext = factory.createSubjectContext(subject);
				subjectContexts.put(subject, subjectContext);
			}
			return subjectContext;
		}
	}

	@Override
	public void setSubjectContextFactory(SubjectContextFactory<C> factory) {
		this.factory = factory;
	}
}
