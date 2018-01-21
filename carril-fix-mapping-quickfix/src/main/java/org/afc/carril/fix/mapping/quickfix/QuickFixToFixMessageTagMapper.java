package org.afc.carril.fix.mapping.quickfix;

import java.util.LinkedList;
import java.util.List;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.TagMapper;
import org.afc.carril.fix.mapping.schema.Use;
import org.afc.carril.message.FixMessage;
import org.afc.carril.transport.AccessorMapping;
import org.afc.carril.transport.TransportException;
import org.afc.carril.transport.util.AccessorMappingRegistry;
import org.afc.util.ObjectUtil;
import org.afc.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Group;

public class QuickFixToFixMessageTagMapper implements TagMapper<quickfix.Message, FixMessage> {
	
	private static final Logger logger = LoggerFactory.getLogger(QuickFixToFixMessageTagMapper.class);

	private String name;

	private Getter<quickfix.Message> getter;

	private Setter<quickfix.Message, FixMessage, Object> setter;
	
	private String targetIndex;

	private Use use;

	private List<TagMapper<quickfix.Message, FixMessage>> tagMappers;

	public QuickFixToFixMessageTagMapper(String name, Getter<quickfix.Message> getter, Setter<quickfix.Message, FixMessage, Object> setter, String targetIndex, Use use) {
		this.name = name;
		this.getter = getter;
		this.setter = setter;
		this.targetIndex = targetIndex;
		this.use = use;
	}

	@Override
	public void addTagMapper(TagMapper<quickfix.Message, FixMessage> tagMapper) {
		if (tagMappers == null) {
			tagMappers = new LinkedList<TagMapper<quickfix.Message, FixMessage>>();
		}
		tagMappers.add(tagMapper);
	}

	@Override
	public FixMessage map(quickfix.Message source, FixMessage target) {
		try {
			if (ObjectUtil.isAnyoneNull(getter, setter)) {
				// handle root tag <tags> under <msg-map> without getter setter
				return mapRootTag(source, target);
			} else {
				if (tagMappers == null) {
					// single field
					return mapSingleField(source, target);
				} else {
					// handle repeating group <tag> with getter setter for the
					// List.class
					return mapRepeatingGroup(source, target);
				}
			}
		} catch (TransportException e) {
			throw e;
		} catch (Exception e) {
			throw new TransportException("Error on mapping tag " + name, e);
		}
	}

	private FixMessage mapRootTag(quickfix.Message source, FixMessage target) {
		if (tagMappers == null) {
			System.out.println("wait");
		}
		for(TagMapper<quickfix.Message, FixMessage> tagMapper : tagMappers) {
			tagMapper.map(source, target);
		}
		return target;
	}

    private FixMessage mapRepeatingGroup(quickfix.Message source, FixMessage target) {
		List<Group> values = ObjectUtil.cast(getter.get(source));
		if (values.size() == 0) {
			return target;
		}
		AccessorMapping accessorMapping = AccessorMappingRegistry.getFixBodyMapping(target, targetIndex);
		
		Class<FixMessage> clazz = ObjectUtil.cast(accessorMapping.getImplClass());
		List<FixMessage> fixFormats = new LinkedList<FixMessage>();
		for (Group group:values) {
			FixMessage fixFormat = ObjectUtil.newInstance(clazz);
			quickfix.Message groupFields = new quickfix.Message();
			groupFields.setFields(group);
			groupFields.setGroups(group);
			for (TagMapper<quickfix.Message, FixMessage> tagMapper:tagMappers) {
				tagMapper.map(groupFields, fixFormat);
			}
			fixFormats.add(fixFormat);
		}
		setter.set(source, target, fixFormats);
		return target;
	}	
	
	private FixMessage mapSingleField(quickfix.Message source, FixMessage target) {
		try {
			Object value = getter.get(source);
			setter.set(source, target, value);
			return target;
		} catch (Exception e) {
			if (use == Use.REQ) {
				throw new TransportException(name + " is a required field.", e);
			} else {
				logger.trace("mapping tag {}, {}", name, e.getMessage());
				return target;
			}
		}
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		StringUtil.startToString(builder);
		StringUtil.buildToString(builder, "name", name);
		StringUtil.buildToString(builder, "getter", getter);
		StringUtil.buildToString(builder, "setter", setter);
		StringUtil.buildToString(builder, "use", use);
		StringUtil.buildToString(builder, "tagMappers", tagMappers);
		StringUtil.endToString(builder);
	    return builder.toString();
	}
}
