package org.afc.carril.fix.mapping.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.afc.carril.fix.mapping.AccessorFactory;
import org.afc.carril.fix.mapping.Getter;
import org.afc.carril.fix.mapping.MsgMapper;
import org.afc.carril.fix.mapping.SessionState;
import org.afc.carril.fix.mapping.Setter;
import org.afc.carril.fix.mapping.TagMapper;
import org.afc.carril.fix.mapping.schema.Dispatchable;
import org.afc.carril.fix.mapping.schema.FixConv;
import org.afc.carril.fix.mapping.schema.MsgMap;
import org.afc.carril.fix.mapping.schema.Reference;
import org.afc.carril.fix.mapping.schema.Tag;
import org.afc.carril.fix.mapping.schema.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.afc.carril.transport.FieldNotFoundException;
import org.afc.carril.transport.TransportException;
import org.afc.util.ObjectUtil;

public class DefaultMsgMapper<S, T> implements MsgMapper<S, T> {

	@SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(DefaultMsgMapper.class);
	
	private AccessorFactory<S, T, Object> accessorFactory;

	private List<DispatchableContext> dispatchableContexts;

	private TagMapper<S, T> tagMapper;
	
	private SessionState state;
	
	private String name;

	private String targetType;

	private FixConv fixConv;

	private class DispatchableContext {
		
		private Getter<S> getter;
		
		private String expression;

		public DispatchableContext(Getter<S> getter, String expression) {
	        this.getter = getter;
	        this.expression = expression;
        }

		public Getter<S> getGetter() {
        	return getter;
        }

		public String getExpression() {
        	return expression;
        }
	}
		
	public DefaultMsgMapper(FixConv fixConv, AccessorFactory<S, T, Object> accessorFactory, MsgMap msgMap, SessionState state) {
		this.accessorFactory = accessorFactory;
		this.fixConv = fixConv;
		this.state = state;
		this.name = msgMap.getName();
		this.targetType= msgMap.getTargetType();
		if (this.targetType == null) {
			throw new TransportException("target-type is not defined for msg-map " + name);
		}
		this.dispatchableContexts = new ArrayList<DispatchableContext>();
		initDispatchableContexts(msgMap, dispatchableContexts); 
		initDispatchableContexts(msgMap.getConditions(), dispatchableContexts);
		this.tagMapper = createTagMapper(msgMap.getTags());
	}
	
	private void initDispatchableContexts(Dispatchable dispatchable, List<DispatchableContext> contexts) {
		try {
			if (dispatchable == null) {
				return;
			}

			if (ObjectUtil.isAllNotNull(dispatchable.getSource(), dispatchable.getSourceIndex(), dispatchable.getSourceExpression())) {  
				Getter<S> getter = accessorFactory.createGetter(fixConv, state, dispatchable.getSource(), dispatchable.getSourceIndex(), Type.STRING);
				contexts.add(new DispatchableContext(getter, dispatchable.getSourceExpression()));
			}
	
			if (dispatchable.getCondition() != null) {
				for (Dispatchable subDispatchable : dispatchable.getCondition()) {
					initDispatchableContexts(subDispatchable, contexts);
				}
	    	}
		} catch (Exception e) {
			throw new TransportException("Error on creating mapping context " + dispatchable.getName() + "." + dispatchable.getSourceIndex(), e);
		}
	}

	private TagMapper<S, T> createTagMapper(Tag rootTag) {
		try {
			List<String> order = order(rootTag);
			
			Getter<S> getter = createGetter(state, rootTag);
			Setter<S, T, Object> setter = createSetter(state, rootTag, order);
			TagMapper<S, T> tagMapper = accessorFactory.createTagMapper(rootTag.getName(), getter, setter, rootTag.getTargetIndex(), rootTag.getUse());
			
			for (Tag tag : rootTag.getTag()) {
				tagMapper.addTagMapper(createTagMapper(tag));
			}
			return tagMapper;
		} catch (Exception e) {
			throw new TransportException("Error on creating tag pair for " + name + '.' + rootTag.getName(), e);
		}
	}
	
	private Getter<S> createGetter(SessionState state, Tag tag) {
		if (ObjectUtil.isAllNotNull(tag.getSource(), tag.getSourceIndex())) {
			return accessorFactory.createGetter(fixConv, state, tag.getSource(), tag.getSourceIndex(), tag.getType());	
		} else {
			return null;
		}
	}

	private Setter<S, T, Object> createSetter(SessionState state, Tag tag, List<String> order) {
		if (ObjectUtil.isAllNotNull(tag.getTarget(), tag.getTargetIndex(), tag.getType())) {
			return accessorFactory.createSetter(fixConv, state, tag.getTarget(), tag.getTargetIndex(), tag.getType(), order);	
		} else {
			return null;
		}
	}
	
	private static List<String> order(Tag tag) {
		List<String> order = tag.getTag().stream()
			.filter(t ->  
				t.getTarget() == Reference.FIX || 
				t.getTarget() == Reference.FIX_HEADER || 
				t.getTarget() == Reference.FIX_TRAILER)
			.map(t -> t.getTargetIndex())
			.collect(Collectors.toList());
		if (order.size() > 0) {
			logger.info("tag:[{}], field order:{}", tag.getName(), order);
			return order;
		} else {
			return null;
		}
	}
	
	@Override
	public T map(S source, T target) {
		return tagMapper.map(source, target);
	}
	
	@Override
	public String match(S msg) {
		try {
			for (DispatchableContext context : dispatchableContexts) {
				Object value = context.getGetter().get(msg);
				
		    	if (value == null || !value.toString().matches(context.getExpression())) {
		    		return null;
		    	} 
			}
		} catch (FieldNotFoundException e) {
			logger.debug("{} for mapper [{}]", e.getMessage(), name);
    		return null;
		}
		logger.info("matched scheme:[{}], target type:[{}]", name, targetType);
   		return targetType;
	}
}	
	
	

