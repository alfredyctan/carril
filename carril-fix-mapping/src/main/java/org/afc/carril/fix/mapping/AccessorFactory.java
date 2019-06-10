package org.afc.carril.fix.mapping;

import java.util.List;

import org.afc.carril.fix.mapping.schema.FixConv;
import org.afc.carril.fix.mapping.schema.Reference;
import org.afc.carril.fix.mapping.schema.Type;
import org.afc.carril.fix.mapping.schema.Use;

/**
 * 
 * @author atyc
 *
 * @param <S> - Source Class Type
 * @param <T> - Target Class Type
 * @param <V> - Value Class Type
 */
public interface AccessorFactory<S, T, V> {

	public Getter<S> createGetter(FixConv fixConv, SessionState state, Reference reference, String index, Type type);
	
	public Setter<S, T, V> createSetter(FixConv fixConv, SessionState state, Reference reference, String index, Type type, List<String> order);

	public TagMapper<S, T> createTagMapper(String name, Getter<S> getter, Setter<S, T, V> setter, String targetIndex, Use use);

}
