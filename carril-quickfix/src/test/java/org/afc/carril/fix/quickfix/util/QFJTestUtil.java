package org.afc.carril.fix.quickfix.util;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.afc.util.ListUtil;
import org.afc.util.MapUtil;
import org.afc.util.ObjectUtil;

import quickfix.FieldMap;
import quickfix.FieldNotFound;
import quickfix.Group;
import quickfix.Message;

public class QFJTestUtil {

	public static void assertMessages(String reason, List<Message> actualMessages, List<Message> expectMessages) {
		assertThat(reason + " - size", actualMessages.size(), is(equalTo(expectMessages.size())));
		ListUtil.coStream(actualMessages, expectMessages).forEach(e -> {
			assertMessage(e.x, e.y);
		});
	}

	public static void assertMessage(Message actual, Message expect) {
		assertFieldMap(actual.getHeader(), expect.getHeader());
		assertFieldMap(actual, expect);
		assertFieldMap(actual.getTrailer(), expect.getTrailer());
	}

	public static void assertFieldMap(FieldMap actual, FieldMap expect) {
		if (ObjectUtil.isAnyoneNull(expect)) {
			fail("actual : [" + actual + "] not match expect : " + expect + "]"); 
		} 
		List<Integer> actualFields = new LinkedList<>();
		List<Integer> expectFields = new LinkedList<>();
		actual.iterator().forEachRemaining(f -> actualFields.add(f.getTag()));
		expect.iterator().forEachRemaining(f -> expectFields.add(f.getTag()));
		assertThat("tags", actualFields, containsInAnyOrder(expectFields.toArray()));
		
		actual.groupKeyIterator().forEachRemaining(groupKey -> {
			actualFields.remove((Object)groupKey);
			List<Group> actualGroups = actual.getGroups(groupKey);
			List<Group> expectGroups = expect.getGroups(groupKey);
			ListUtil.coStream(actualGroups, expectGroups).forEach(e -> {
				assertFieldMap(e.x, e.y);
			});
		});
		
		actualFields.forEach(tag -> {
			try {
				assertThat("tag : " + tag, actual.getString(tag), is(equalTo(expect.getString(tag))));
			} catch (FieldNotFound e) {
				fail("tag " + tag + " not found in actual or expect");
			}
		});
	}

	public static Message header(Message message, String version, String msgType, String sender, String target, String onBehalfOfCompID, String deliverToCompID) {
		header(message, version, msgType, sender, target);
		fieldMap(message.getHeader(), 115, onBehalfOfCompID, 128, deliverToCompID);
		return 	message;
	}

	public static Message header(Message message, String version, String msgType, String sender, String target) {
		fieldMap(message.getHeader(), 8, version, 35, msgType, 49, sender, 56, target);
		return 	message;
	}

	public static Message trailer(Message message, Object... fields) {
		fieldMap(message.getTrailer(), fields);
		return 	message;
	}

	public static Message group(Message message, int num, int groupTag, int delim, Object... fields) {
		try {
			fieldMap(message.getGroup(num, groupTag), fields);
		} catch (FieldNotFound e) {
			Group group = new Group(groupTag, delim);
			fieldMap(group, fields);
			message.addGroup(group);
		}
		return message;
	}

	public static FieldMap fieldMap(FieldMap fieldMap, Object... fields) {
		Map<Integer, String> map = MapUtil.map(new HashMap<>(), fields);
		map.forEach((k, v) -> fieldMap.setString(k, v));
		return fieldMap;
	}
}
