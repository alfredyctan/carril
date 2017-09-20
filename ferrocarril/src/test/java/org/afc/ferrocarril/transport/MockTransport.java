package org.afc.ferrocarril.transport;

import java.util.List;

import org.afc.ferrocarril.converter.Converter;
import org.afc.ferrocarril.message.GenericMessage;
import org.afc.ferrocarril.subscriber.Subscriber;
import org.afc.ferrocarril.subscriber.SubscriberRegistry;
import org.afc.ferrocarril.transport.impl.AbstractTransport;
import org.afc.ferrocarril.transport.impl.DefaultSubjectContext;


class MockTransport extends AbstractTransport {

	private boolean init;
	
	private boolean start;
	
	private boolean stop;
	
	private boolean dispose;
	
	private boolean publish;
	
	@Override
    protected void doInit() {
	    init = true;
    }

	@Override
    protected void doStart() {
	    start = true;
    }

	@Override
    protected void doStop() {
	    stop = true;
    }

	@Override
    protected void doDispose() {
		dispose = true;
    }

	@Override
    public SubjectContext createSubjectContext(String subject) {
	    return new DefaultSubjectContext(subject);
    }

	@Override
    public Object getBaseImplementation() {
	    return "IMPL";
    }

	@Override
    public void publish(String subject, GenericMessage fmtObj, Converter<Object, GenericMessage> converter) throws TransportException {
		publish = true;
		if (subject == null) {
			fireOnException(new TransportException("null"));
		}
    }

	@Override
    public <G extends GenericMessage> G publishRequest(String subject, GenericMessage fmtObj, Class<? extends GenericMessage> clazz,
                                        Converter<Object, GenericMessage> converter, int timeout) throws TransportException {
	    return (G) new MockMessage();
    }

	@Override
    public Subscriber createSubscriber(String subject, TransportListener transportListener,
                                           Class<? extends GenericMessage> clazz,
                                           Converter<Object, GenericMessage> converter) {
	    return new MockSubscriber();
    }

	public boolean isInit() {
    	return init;
    }

	public void setInit(boolean init) {
    	this.init = init;
    }

	public boolean isStart() {
    	return start;
    }

	public void setStart(boolean start) {
    	this.start = start;
    }

	public boolean isStop() {
    	return stop;
    }

	public void setStop(boolean stop) {
    	this.stop = stop;
    }

	public boolean isDispose() {
    	return dispose;
    }

	public void setDispose(boolean dispose) {
    	this.dispose = dispose;
    }

	public boolean isPublish() {
    	return publish;
    }

	public void setPublish(boolean publish) {
    	this.publish = publish;
    }

	public SubscriberRegistry getRegistry() {
    	return registry;
    }

	public void setRegistry(SubscriberRegistry registry) {
    	this.registry = registry;
    }

	public List<ExceptionListener<? extends TransportException>> getExptListeners() {
    	return exptListeners;
    }

	public void setExptListeners(List<ExceptionListener<? extends TransportException>> exptListeners) {
    	this.exptListeners = exptListeners;
    }

	public Converter<Object, GenericMessage> getConverter() {
    	return converter;
    }

}
