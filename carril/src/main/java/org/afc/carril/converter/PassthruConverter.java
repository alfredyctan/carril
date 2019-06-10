package org.afc.carril.converter;

import org.afc.carril.message.CrudeMessage;
import org.afc.carril.transport.TransportException;

import org.afc.util.ObjectUtil;

public class PassthruConverter<T> implements Converter<T, CrudeMessage<T>> {

	@Override
	public CrudeMessage<T> parse(T wireFormat, Class<? extends CrudeMessage<T>> clazz) throws TransportException {
		CrudeMessage<T> obj = ObjectUtil.newInstance(clazz);
		obj.setCrude(wireFormat);
		return obj;
	}

	@Override
	public T format(CrudeMessage<T> message) throws TransportException {
		return message.getCrude();
	}
}
