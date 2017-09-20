package org.afc.ferrocarril.fix.mapping.rawfix;

import java.util.LinkedList;
import java.util.List;

import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.Setter;
import org.afc.ferrocarril.fix.mapping.TagMapper;
import org.afc.ferrocarril.fix.mapping.schema.Use;
import org.afc.ferrocarril.message.FixMessage;
import org.afc.ferrocarril.transport.AccessorMapping;
import org.afc.ferrocarril.transport.TransportException;
import org.afc.util.ObjectUtil;
import org.afc.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawFixToFixMessageTagMapper implements TagMapper<RawFix, FixMessage> {
	
	private static final Logger logger = LoggerFactory.getLogger(RawFixToFixMessageTagMapper.class);

	private String name;

	private Getter<RawFix> getter;

	private Setter<RawFix, FixMessage, Object> setter;
	
	private String targetIndex;

	private Use use;

	private List<TagMapper<RawFix, FixMessage>> tagMappers;

	public RawFixToFixMessageTagMapper(String name, Getter<RawFix> getter, Setter<RawFix, FixMessage, Object> setter, String targetIndex, Use use) {
		this.name = name;
		this.getter = getter;
		this.setter = setter;
		this.targetIndex = targetIndex;
		this.use = use;
	}

	@Override
	public void addTagMapper(TagMapper<RawFix, FixMessage> tagMapper) {
		if (tagMappers == null) {
			tagMappers = new LinkedList<TagMapper<RawFix, FixMessage>>();
		}
		tagMappers.add(tagMapper);
	}

	@Override
	public FixMessage map(RawFix source, FixMessage target) {
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

	
	private FixMessage mapRootTag(RawFix source, FixMessage target) {
		for(TagMapper<RawFix, FixMessage> tagMapper : tagMappers) {
			source.setIndex(0); //reset the index to search from the beginning
			tagMapper.map(source, target);
		}
		return target;
	}

    private FixMessage mapRepeatingGroup(RawFix source, FixMessage target) {
    	int count = 0;
    	Integer noOf = ObjectUtil.cast(getter.get(source));
    	if (noOf != null) {
    		count = noOf.intValue();
    	}

    	AccessorMapping rpGrpAccessorMapping = target.getFixMessageMap().get(targetIndex); //targetIndex is the repeating group index
		Class<FixMessage> rpGrpClazz = ObjectUtil.cast(rpGrpAccessorMapping.getImplClass());
		List<FixMessage> rpGrpFixFormats = new LinkedList<FixMessage>();
    	int maxIndex = source.getIndex();
		for (int i = 0; i < count; i++) {
			FixMessage rpGrpFixFormat = ObjectUtil.newInstance(rpGrpClazz);
	    	int lastIndex = maxIndex;
			for (TagMapper<RawFix, FixMessage> tagMapper:tagMappers) {
				source.setIndex(lastIndex);
				tagMapper.map(source, rpGrpFixFormat);
				maxIndex = Math.max(maxIndex, source.getIndex());
			}
			rpGrpFixFormats.add(rpGrpFixFormat);
		}
		if (rpGrpFixFormats.size() > 0) {
			setter.set(source, target, rpGrpFixFormats);
		}
		return target;
	}	
	
	private FixMessage mapSingleField(RawFix source, FixMessage target) {
		try {
			Object value = getter.get(source);
			setter.set(source, target, value);
			return target;
		} catch (Exception e) {
			if (use == Use.REQ) {
				throw new TransportException(name + " is a required field.", e);
			} else {
				logger.trace("Mapping tag {}, {}", name, e.getMessage());
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
