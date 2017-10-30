package org.afc.carril.fix.mapping.quickfix;

import java.util.LinkedList;
import java.util.List;

import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.TagMapper;
import org.afc.carril.fix.mapping.schema.Use;
import org.afc.carril.message.QuickFixMessage;
import org.afc.carril.transport.AccessorMapping;
import org.afc.carril.transport.TransportException;
import org.afc.util.ObjectUtil;
import org.afc.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Group;

public class QuickFixToFixMessageTagMapper implements TagMapper<quickfix.Message, QuickFixMessage> {
	
	private static final Logger logger = LoggerFactory.getLogger(QuickFixToFixMessageTagMapper.class);

	private String name;

	private Getter<quickfix.Message> getter;

	private Setter<quickfix.Message, QuickFixMessage, Object> setter;
	
	private String targetIndex;

	private Use use;

	private List<TagMapper<quickfix.Message, QuickFixMessage>> tagMappers;

	public QuickFixToFixMessageTagMapper(String name, Getter<quickfix.Message> getter, Setter<quickfix.Message, QuickFixMessage, Object> setter, String targetIndex, Use use) {
		this.name = name;
		this.getter = getter;
		this.setter = setter;
		this.targetIndex = targetIndex;
		this.use = use;
	}

	@Override
	public void addTagMapper(TagMapper<quickfix.Message, QuickFixMessage> tagMapper) {
		if (tagMappers == null) {
			tagMappers = new LinkedList<TagMapper<quickfix.Message, QuickFixMessage>>();
		}
		tagMappers.add(tagMapper);
	}

	@Override
	public QuickFixMessage map(quickfix.Message source, QuickFixMessage target) {
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

	private QuickFixMessage mapRootTag(quickfix.Message source, QuickFixMessage target) {
		if (tagMappers == null) {
			System.out.println("wait");
		}
		for(TagMapper<quickfix.Message, QuickFixMessage> tagMapper : tagMappers) {
			tagMapper.map(source, target);
		}
		return target;
	}

    private QuickFixMessage mapRepeatingGroup(quickfix.Message source, QuickFixMessage target) {
		List<Group> values = ObjectUtil.cast(getter.get(source));
		if (values.size() == 0) {
			return target;
		}
		AccessorMapping accessorMapping = target.getFixMessageMap().get(targetIndex);
		
		Class<QuickFixMessage> clazz = ObjectUtil.cast(accessorMapping.getImplClass());
		List<QuickFixMessage> fixFormats = new LinkedList<QuickFixMessage>();
		for (Group group:values) {
			QuickFixMessage fixFormat = ObjectUtil.newInstance(clazz);
			quickfix.Message groupFields = new quickfix.Message();
			groupFields.setFields(group);
			groupFields.setGroups(group);
			for (TagMapper<quickfix.Message, QuickFixMessage> tagMapper:tagMappers) {
				tagMapper.map(groupFields, fixFormat);
			}
			fixFormats.add(fixFormat);
		}
		setter.set(source, target, fixFormats);
		return target;
	}	
	
	private QuickFixMessage mapSingleField(quickfix.Message source, QuickFixMessage target) {
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
