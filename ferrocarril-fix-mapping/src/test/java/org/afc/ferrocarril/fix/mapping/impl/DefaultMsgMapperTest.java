package org.afc.ferrocarril.fix.mapping.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.afc.ferrocarril.fix.mapping.AccessorFactory;
import org.afc.ferrocarril.fix.mapping.Getter;
import org.afc.ferrocarril.fix.mapping.SessionState;
import org.afc.ferrocarril.fix.mapping.Setter;
import org.afc.ferrocarril.fix.mapping.TagMapper;
import org.afc.ferrocarril.fix.mapping.schema.MsgMap;
import org.afc.ferrocarril.fix.mapping.schema.Reference;
import org.afc.ferrocarril.fix.mapping.schema.Tag;
import org.afc.ferrocarril.fix.mapping.schema.Type;
import org.afc.ferrocarril.fix.mapping.schema.Use;
import org.afc.ferrocarril.message.FixMessage;
import org.afc.util.JUnit4Util;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DefaultMsgMapperTest {

	Mockery context;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		context = new JUnit4Mockery();
	}

	@After
	public void tearDown() throws Exception {
		context.assertIsSatisfied();
	}

	@Test
	public void testMap() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			MsgMap msgMap = new MsgMap();
			msgMap.setName("A");
			msgMap.setSource(Reference.OBJ);
			msgMap.setSourceIndex("getA");
			msgMap.setSourceExpression("999");
			msgMap.setTargetType("T");
			msgMap.setTags(new Tag());
			msgMap.getTags().getTag()
					.add(createTag("MSG-ID", Type.STRING, Reference.SCHEMA, "Msg1A", Reference.OBJ, "MsgID", Use.REQ));
			msgMap.getTags().getTag().add(
					createTag("MSG-ID2", Type.STRING, Reference.SCHEMA, "Msg1B", Reference.OBJ, "MsgID2", Use.REQ));

			final AccessorFactory accessorFactory = context.mock(AccessorFactory.class);
			final SessionState state = context.mock(SessionState.class);
			final Setter setter = context.mock(Setter.class);
			final Getter getter = context.mock(Getter.class);
			final TagMapper tagMapper = context.mock(TagMapper.class);

			final Object input = new Object();
			final FixMessage actual = context.mock(FixMessage.class);

			context.checking(new Expectations() {
				{
					oneOf(accessorFactory).createGetter(state, Reference.OBJ, "getA", Type.STRING);
					will(returnValue(getter));

					oneOf(accessorFactory).createTagMapper(null, null, null, null, Use.OPT);
					will(returnValue(tagMapper));

					oneOf(accessorFactory).createGetter(state, Reference.SCHEMA, "Msg1A", Type.STRING);
					will(returnValue(getter));

					oneOf(accessorFactory).createSetter(state, Reference.OBJ, "MsgID", Type.STRING);
					will(returnValue(setter));

					oneOf(accessorFactory).createTagMapper("MSG-ID", getter, setter, "MsgID", Use.REQ);
					will(returnValue(tagMapper));

					oneOf(tagMapper).addTagMapper(tagMapper);

					oneOf(accessorFactory).createGetter(state, Reference.SCHEMA, "Msg1B", Type.STRING);
					will(returnValue(getter));

					oneOf(accessorFactory).createSetter(state, Reference.OBJ, "MsgID2", Type.STRING);
					will(returnValue(setter));

					oneOf(accessorFactory).createTagMapper("MSG-ID2", getter, setter, "MsgID2", Use.REQ);
					will(returnValue(tagMapper));

					oneOf(tagMapper).addTagMapper(tagMapper);

					oneOf(tagMapper).map(input, actual);
					will(returnValue(actual));

				}
			});

			DefaultMsgMapper mapper = new DefaultMsgMapper(accessorFactory, msgMap, state);

			assertNotNull(mapper.map(input, actual));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testMatch() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			MsgMap msgMap = new MsgMap();
			msgMap.setName("A");
			msgMap.setSource(Reference.OBJ);
			msgMap.setSourceIndex("getA");
			msgMap.setSourceExpression("999");
			msgMap.setTargetType("T");
			msgMap.setTags(new Tag());
			msgMap.getTags().getTag()
					.add(createTag("MSG-ID", Type.STRING, Reference.SCHEMA, "Msg1A", Reference.OBJ, "MsgID", Use.REQ));
			msgMap.getTags().getTag().add(
					createTag("MSG-ID2", Type.STRING, Reference.SCHEMA, "Msg1B", Reference.OBJ, "MsgID2", Use.REQ));

			final AccessorFactory accessorFactory = context.mock(AccessorFactory.class);
			final SessionState state = context.mock(SessionState.class);
			final Setter setter = context.mock(Setter.class);
			final Getter getter = context.mock(Getter.class);
			final TagMapper tagMapper = context.mock(TagMapper.class);

			final Object input = new Object();

			context.checking(new Expectations() {
				{
					oneOf(accessorFactory).createGetter(state, Reference.OBJ, "getA", Type.STRING);
					will(returnValue(getter));

					oneOf(accessorFactory).createTagMapper(null, null, null, null, Use.OPT);
					will(returnValue(tagMapper));

					oneOf(accessorFactory).createGetter(state, Reference.SCHEMA, "Msg1A", Type.STRING);
					will(returnValue(getter));

					oneOf(accessorFactory).createSetter(state, Reference.OBJ, "MsgID", Type.STRING);
					will(returnValue(setter));

					oneOf(accessorFactory).createTagMapper("MSG-ID", getter, setter, "MsgID", Use.REQ);
					will(returnValue(tagMapper));

					oneOf(tagMapper).addTagMapper(tagMapper);

					oneOf(accessorFactory).createGetter(state, Reference.SCHEMA, "Msg1B", Type.STRING);
					will(returnValue(getter));

					oneOf(accessorFactory).createSetter(state, Reference.OBJ, "MsgID2", Type.STRING);
					will(returnValue(setter));

					oneOf(accessorFactory).createTagMapper("MSG-ID2", getter, setter, "MsgID2", Use.REQ);
					will(returnValue(tagMapper));

					oneOf(tagMapper).addTagMapper(tagMapper);

					oneOf(getter).get(input);
					will(returnValue("999"));
				}
			});

			DefaultMsgMapper mapper = new DefaultMsgMapper(accessorFactory, msgMap, state);

			assertEquals("T", mapper.match(input));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		JUnit4Util.endCurrentTest(getClass());
	}

	@Test
	public void testUnmatch() {
		JUnit4Util.startCurrentTest(getClass());
		try {
			MsgMap msgMap = new MsgMap();
			msgMap.setName("A");
			msgMap.setSource(Reference.OBJ);
			msgMap.setSourceIndex("getA");
			msgMap.setSourceExpression("999");
			msgMap.setTargetType("T");
			msgMap.setTags(new Tag());
			msgMap.getTags().getTag()
					.add(createTag("MSG-ID", Type.STRING, Reference.SCHEMA, "Msg1A", Reference.OBJ, "MsgID", Use.REQ));
			msgMap.getTags().getTag().add(
					createTag("MSG-ID2", Type.STRING, Reference.SCHEMA, "Msg1B", Reference.OBJ, "MsgID2", Use.REQ));

			final AccessorFactory accessorFactory = context.mock(AccessorFactory.class);
			final SessionState state = context.mock(SessionState.class);
			final Setter setter = context.mock(Setter.class);
			final Getter getter = context.mock(Getter.class);
			final TagMapper tagMapper = context.mock(TagMapper.class);

			final Object input = new Object();

			context.checking(new Expectations() {
				{
					oneOf(accessorFactory).createGetter(state, Reference.OBJ, "getA", Type.STRING);
					will(returnValue(getter));

					oneOf(accessorFactory).createTagMapper(null, null, null, null, Use.OPT);
					will(returnValue(tagMapper));

					oneOf(accessorFactory).createGetter(state, Reference.SCHEMA, "Msg1A", Type.STRING);
					will(returnValue(getter));

					oneOf(accessorFactory).createSetter(state, Reference.OBJ, "MsgID", Type.STRING);
					will(returnValue(setter));

					oneOf(accessorFactory).createTagMapper("MSG-ID", getter, setter, "MsgID", Use.REQ);
					will(returnValue(tagMapper));

					oneOf(tagMapper).addTagMapper(tagMapper);

					oneOf(accessorFactory).createGetter(state, Reference.SCHEMA, "Msg1B", Type.STRING);
					will(returnValue(getter));

					oneOf(accessorFactory).createSetter(state, Reference.OBJ, "MsgID2", Type.STRING);
					will(returnValue(setter));

					oneOf(accessorFactory).createTagMapper("MSG-ID2", getter, setter, "MsgID2", Use.REQ);
					will(returnValue(tagMapper));

					oneOf(tagMapper).addTagMapper(tagMapper);

					oneOf(getter).get(input);
					will(returnValue("99"));
				}
			});

			DefaultMsgMapper mapper = new DefaultMsgMapper(accessorFactory, msgMap, state);

			assertNull("Should be null", mapper.match(input));
		} catch (Exception e) {
			e.printStackTrace();
			fail(e.getMessage());
		}
		JUnit4Util.endCurrentTest(getClass());
	}

	private static Tag createTag(String name, Type type, Reference source, String sourceIndex, Reference target,
			String targetIndex, Use use) {
		Tag tag = new Tag();
		tag.setName(name);
		tag.setType(type);
		tag.setSource(source);
		tag.setSourceIndex(sourceIndex);
		tag.setTarget(target);
		tag.setTargetIndex(targetIndex);
		tag.setUse(use);
		return tag;
	}
}
