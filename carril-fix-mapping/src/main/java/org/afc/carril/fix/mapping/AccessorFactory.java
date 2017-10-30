package org.afc.carril.fix.mapping;

import org.afc.carril.fix.mapping.schema.Reference;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.fix.mapping.schema.Use;

public interface AccessorFactory<S, T, V> {

	public Getter<S> createGetter(SessionState state, Reference reference, String index, Type type);
	
	public Setter<S, T, V> createSetter(SessionState state, Reference reference, String index, Type type);

	public TagMapper<S, T> createTagMapper(String name, Getter<S> getter, Setter<S, T, V> setter, String targetIndex, Use use);

}
