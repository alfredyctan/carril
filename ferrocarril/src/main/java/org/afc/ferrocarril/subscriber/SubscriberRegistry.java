package org.afc.ferrocarril.subscriber;

import java.util.Collection;
import java.util.Map;

import org.afc.ferrocarril.transport.SubjectContext;
import org.afc.ferrocarril.transport.SubjectContextFactory;


public interface SubscriberRegistry<C extends SubjectContext> {

	public Collection<C> allSubjectContexts();
	
	public void register(String subject, Subscriber subscription);

	public void register(String subject, C subjectContext);

	public C unregister(String subject);
	
	public Map<String, C> unregisterAll();
	
	public C getSubjectContext(String subject);

	public void setSubjectContextFactory(SubjectContextFactory<C> factory);
}
