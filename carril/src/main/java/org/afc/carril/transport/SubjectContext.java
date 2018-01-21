package org.afc.carril.transport;

import java.util.List;

import org.afc.carril.publisher.Publisher;
import org.afc.carril.subscriber.Subscriber;

public interface SubjectContext {

	public String getSubject();
	
	public List<Subscriber> getSubscribers();
	
	public void addSubscriber(Subscriber subscriber);

	public void removeSubscriber(Subscriber subscriber);

	public List<Publisher> getPublishers();
	
	public void addPublisher(Publisher publisher);

	public void removePublisher(Publisher publisher);
}
