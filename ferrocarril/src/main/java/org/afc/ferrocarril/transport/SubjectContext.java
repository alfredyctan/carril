package org.afc.ferrocarril.transport;

import java.util.List;

import org.afc.ferrocarril.subscriber.Subscriber;

public interface SubjectContext {

	public String getSubject();
	
	public List<Subscriber> getSubscribers();
	
	public void addSubscriber(Subscriber subscription);

	public void removeSubscriber(Subscriber subscription);

}
