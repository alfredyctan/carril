package org.afc.carril.transport.impl;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.afc.carril.transport.SubjectContext;
import org.afc.carril.transport.SubjectRegistry;

import lombok.ToString;

@ToString
public class DefaultSubjectRegistry<C extends SubjectContext> implements SubjectRegistry<C> {

	private Map<String, C> subjectContexts;

	public DefaultSubjectRegistry() {
		this.subjectContexts = new ConcurrentHashMap<String, C>();
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
			return subjectContext;
		}
	}

	@Override
	public C getSubjectContext(String subject) {
		return subjectContexts.get(subject);
	}

	@Override
	public Collection<C> getSubjectContexts() {
		return subjectContexts.values();
	}

	@Override
	public Map<String, C> unregisterAll() {
		Map<String, C> unregistered = subjectContexts;
		subjectContexts = new ConcurrentHashMap<String, C>();
		return unregistered;
	}
}
