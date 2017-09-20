package org.afc.ferrocarril.fix.mapping.impl;

import java.util.ArrayList;
import java.util.List;

import org.afc.ferrocarril.fix.mapping.AccessorFactory;
import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.MsgMapper;
import org.afc.ferrocarril.fix.mapping.SessionState;
import org.afc.ferrocarril.fix.mapping.Setter;
import org.afc.ferrocarril.fix.mapping.TagMapper;
import org.afc.ferrocarril.fix.mapping.schema.Dispatchable;
import org.afc.ferrocarril.fix.mapping.schema.MsgMap;
import org.afc.ferrocarril.fix.mapping.schema.Tag;
import org.afc.ferrocarril.fix.mapping.schema.Type;
import org.afc.ferrocarril.transport.TransportException;
import org.afc.util.ObjectUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultMsgMapper<S, T> implements MsgMapper<S, T> {

	@SuppressWarnings("unused")
    private static final Logger logger = LoggerFactory.getLogger(DefaultMsgMapper.class);
	
	private AccessorFactory<S, T, Object> accessorFactory;

	private List<DispatchableContext> dispatchableContexts;

	private TagMapper<S, T> tagMapper;
	
	private SessionState state;
	
	private String name;

	private String targetType;

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
		
	public DefaultMsgMapper(AccessorFactory<S, T, Object> accessorFactory, MsgMap msgMap, SessionState state) {
		this.accessorFactory = accessorFactory;
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
				Getter<S> getter = accessorFactory.createGetter(state, dispatchable.getSource(), dispatchable.getSourceIndex(), Type.STRING);
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
			if (rootTag.getTag() == null) {
				Getter<S> getter = createGetter(state, rootTag);
				Setter<S, T, Object> setter = createSetter(state, rootTag);
		    	return accessorFactory.createTagMapper(rootTag.getName(), getter, setter, rootTag.getTargetIndex(), rootTag.getUse());
			} else {
				Getter<S> getter = createGetter(state, rootTag);
				Setter<S, T, Object> setter = createSetter(state, rootTag);
				TagMapper<S, T> tagMapper = accessorFactory.createTagMapper(rootTag.getName(), getter, setter, rootTag.getTargetIndex(), rootTag.getUse());
				for (Tag tag : rootTag.getTag()) {
					tagMapper.addTagMapper(createTagMapper(tag));
				}
				return tagMapper;
			}
		} catch (Exception e) {
			throw new TransportException("Error on creating tag pair for " + name + '.' + rootTag.getName(), e);
		}
	}
	
	private Getter<S> createGetter(SessionState state, Tag tag) {
		if (ObjectUtil.isAllNotNull(tag.getSource(), tag.getSourceIndex())) {
			return accessorFactory.createGetter(state, tag.getSource(), tag.getSourceIndex(), tag.getType());	
		} else {
			return null;
		}
	}

	private Setter<S, T, Object> createSetter(SessionState state, Tag tag) {
		if (ObjectUtil.isAllNotNull(tag.getTarget(), tag.getTargetIndex(), tag.getType())) {
			return accessorFactory.createSetter(state, tag.getTarget(), tag.getTargetIndex(), tag.getType());	
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
		for (DispatchableContext context:dispatchableContexts) {
			Object value = context.getGetter().get(msg);
			
	    	if (value == null || !value.toString().matches(context.getExpression())) {
	    		return null;
	    	} 
		}
   		return targetType;
	}
}	
	
	

