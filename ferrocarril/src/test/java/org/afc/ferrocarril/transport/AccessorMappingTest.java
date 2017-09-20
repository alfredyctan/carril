package org.afc.ferrocarril.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccessorMappingTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateAccessorMapping() {
        try {
        	AccessorMapping expect = new AccessorMapping("object", MockMessage.class.getMethod("getNative"), MockMessage.class.getMethod("setNative", Object.class), Object.class);
			AccessorMapping actual = AccessorMapping.createAccessorMapping(MockMessage.class, "object",     "getNative", "setNative",     Object.class);

			assertEquals("Declare",   expect.getDeclareClass(), actual.getDeclareClass());
			assertEquals("FieldName", expect.getFieldName(), actual.getFieldName());
			assertEquals("Format",    expect.getFormat(),    actual.getFormat());
			assertEquals("GetMethod", expect.getGetMethod(), actual.getGetMethod());
			assertEquals("ImplClass", expect.getImplClass(), actual.getImplClass());
			assertEquals("SetMethod", expect.getSetMethod(), actual.getSetMethod());
        } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        } 
		
		
	}

	@Test
	public void testCreateAccessorMappingList() {
		try {
			List<AccessorMapping> expect = new ArrayList<AccessorMapping>(); 
			expect.add(new AccessorMapping("object", MockMessage.class.getMethod("getNative"), MockMessage.class.getMethod("setNative", Object.class), Object.class));
			expect.add(new AccessorMapping("Product", MockMessage.class.getMethod("getProduct"), MockMessage.class.getMethod("setProduct", String.class), String.class));

			List<AccessorMapping> actual = AccessorMapping.createAccessorMappingList(
				AccessorMapping.createAccessorMapping(MockMessage.class, "object",     "getNative", "setNative",     Object.class),                                   
				AccessorMapping.createAccessorMapping(MockMessage.class, "Product",   "getProduct", "setProduct",    String.class)                                   
			);
		
			assertEquals(expect, actual);
        } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        } 
	}

	@Test
	public void testCreateAccessorMappingMap() {
		try {
			Map<String, AccessorMapping> expect = new HashMap<String, AccessorMapping>();
			expect.put("object", new AccessorMapping("object", MockMessage.class.getMethod("getNative"), MockMessage.class.getMethod("setNative", Object.class), Object.class));
			expect.put("Product", new AccessorMapping("Product", MockMessage.class.getMethod("getProduct"), MockMessage.class.getMethod("setProduct", String.class), String.class));

			Map<String, AccessorMapping> actual = AccessorMapping.createAccessorMappingMap(
				AccessorMapping.createAccessorMapping(MockMessage.class, "object",     "getNative", "setNative",     Object.class),                                   
				AccessorMapping.createAccessorMapping(MockMessage.class, "Product",   "getProduct", "setProduct",    String.class)                                   
			);
			assertEquals(expect, actual);
        } catch (Exception e) {
	        e.printStackTrace();
	        fail(e.getMessage());
        } 
	}
}
