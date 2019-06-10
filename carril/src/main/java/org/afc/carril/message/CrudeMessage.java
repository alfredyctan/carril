package org.afc.carril.message;

public interface CrudeMessage<T> extends GenericMessage {

	public T getCrude();
	
	public void setCrude(T crude);
	
}
