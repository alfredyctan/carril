package org.afc.carril.fix.mapping.quickfix;

import java.util.LinkedList;
import java.util.List;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.TagMapper;
import org.afc.carril.fix.mapping.schema.Use;
import org.afc.carril.transport.TransportException;

import org.afc.util.ObjectUtil;

import quickfix.Message;

public class FixMessageToQuickFixTagMapper implements TagMapper<Object, Message> {

	private String name;

	private Getter<Object> getter;

	private Setter<Object, Message, Object> setter;

	private Use use;

	private List<TagMapper<Object, Message>> tagMappers;

	public FixMessageToQuickFixTagMapper(String name, Getter<Object> getter, Setter<Object, Message, Object> setter,
			Use use) {
		this.name = name;
		this.getter = getter;
		this.setter = setter;
		this.use = use;
	}

	@Override
	public void addTagMapper(TagMapper<Object, Message> tagMapper) {
		if (tagMappers == null) {
			tagMappers = new LinkedList<TagMapper<Object, Message>>();
		}

		tagMappers.add(tagMapper);
	}

	@Override
	public Message map(Object source, Message target) {
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

	private Message mapRootTag(Object source, Message target) {
		for (TagMapper<Object, Message> tagMapper : tagMappers) {
			tagMapper.map(source, target);
		}
		return target;
	}

	private Message mapRepeatingGroup(Object source, Message target) {
		List<Object> values = ObjectUtil.<List<Object>>cast(getter.get(source));
		if (values == null) {
			return target;
		}
		for (Object fixFormat : values) {
			// since only repeating group's setter known the group tag id
			// and Group object is only able to set id when creation
			// so need groupFields as temp field container for nested fields
			Message groupFields = new Message();
			for (TagMapper<Object, Message> tagMapper : tagMappers) {
				tagMapper.map(fixFormat, groupFields);
			}
			setter.set(source, target, groupFields);
		}
		return target;
	}

	private Message mapSingleField(Object source, Message target) {
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

	@Override
	public String toString() {
		return "FixMessageToQuickFixTagMapper [name=" + name + ", getter=" + getter + ", setter=" + setter + ", use="
				+ use + ", tagMappers=" + tagMappers + "]";
	}

}
