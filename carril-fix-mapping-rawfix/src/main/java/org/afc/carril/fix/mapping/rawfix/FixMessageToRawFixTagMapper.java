package org.afc.carril.fix.mapping.rawfix;

import java.util.LinkedList;
import java.util.List;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.TagMapper;
import org.afc.carril.fix.mapping.schema.Use;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.TransportException;
import org.afc.util.ObjectUtil;

public class FixMessageToRawFixTagMapper implements TagMapper<FixMessage, RawFix> {

	private String name;
	
	private Getter<FixMessage> getter;
	
	private Setter<FixMessage, RawFix, Object> setter;

	private Use use;

	private List<TagMapper<FixMessage, RawFix>> tagMappers;
	
	public FixMessageToRawFixTagMapper(String name, Getter<FixMessage> getter, Setter<FixMessage, RawFix, Object> setter, Use use) {
		this.name = name;
        this.getter = getter;
        this.setter = setter;
        this.use = use;
    }

	
	@Override
	public void addTagMapper(TagMapper<FixMessage, RawFix> tagMapper) {
		if (tagMappers == null) {
			tagMappers = new LinkedList<TagMapper<FixMessage, RawFix>>();
		}
		tagMappers.add(tagMapper);
	}

	@Override
	public RawFix map(FixMessage source, RawFix target) {
		try {
			if (ObjectUtil.isAnyoneNull(getter, setter)) { 
				// handle root tag <tags> under <msg-map> without getter setter
				return mapRootTag(source, target);
			} else {
				if (tagMappers != null) {
					// handle repeating group <tag> with getter setter for the List.class
					return mapRepeatingGroup(source, target);
				} else {
					// single field
					return mapSingleField(source, target);
				}
			}
		} catch (Exception e) {
			throw new TransportException("Error on mapping tag " + name, e);
		}
	}

	private RawFix mapRootTag(FixMessage source, RawFix target) {
		for (TagMapper<FixMessage, RawFix> tagMapper:tagMappers) {
			tagMapper.map(source, target);
		}
		return target;
	}
	
    private RawFix mapRepeatingGroup(FixMessage source, RawFix target) {
		List<FixMessage> values = ObjectUtil.cast(getter.get(source));
		if (values == null) {
			return target;
		}
		setter.set(source, target, values.size());
		for (FixMessage fixFormat:values) {
//			RawFix groupFields = new RawFix(); 
			for (TagMapper<FixMessage, RawFix> tagMapper:tagMappers) {
				tagMapper.map(fixFormat, target);
			}
		}
		return target;
	}	
	
	private RawFix mapSingleField(FixMessage source, RawFix target) {
		try {
			Object value = getter.get(source);
			setter.set(source, target, value);
			return target;
		} catch (Exception e) {
			if (use == Use.REQ) {
				throw new TransportException(name + " is a required field.", e);
			} else {
				return target;
			}
		}
	}
}
