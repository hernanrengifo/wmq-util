package com.googlecode.wmqutils.headers;

import junit.framework.TestCase;

// <usr>
//	<bo dt='boolean'>0</bo>
//	<ob>foooo</ob>
//  <sh dt='i2'>23</sh>
//  <bo2 dt='boolean'>1</bo2>
//  <int dt='i4'>45</int>
//  <lo dt='i8'>2423</lo>
//  <by dt='i1'>3</by>
//  <fl dt='r4'>123.789</fl>
//  <double dt='r8'>123.567</double>
//  <null xsi:nil='true'></null>
// </usr>
public class UsrAreaTest extends TestCase {

	public void testMatchingLength() {
		UsrArea area = new UsrArea();
		area.setStringProperty("foo", "ba");
		assertEquals("<usr><foo>ba</foo></usr>", area.toString());
	}

	public void testOneOverLength() {
		UsrArea area = new UsrArea();
		area.setStringProperty("foo", "bar");
		assertEquals("<usr><foo>bar</foo></usr>   ", area.toString());
	}

	public void testTwoOverLength() {
		UsrArea area = new UsrArea();
		area.setStringProperty("foo", "barr");
		assertEquals("<usr><foo>barr</foo></usr>  ", area.toString());
	}

	public void testThreeOverLength() {
		UsrArea area = new UsrArea();
		area.setStringProperty("foo", "barrr");
		assertEquals("<usr><foo>barrr</foo></usr> ", area.toString());
	}

	public void testSetStringProperty() {
		UsrArea area = new UsrArea();
		
		assertNull(area.getStringProperty("foo"));
		area.setStringProperty("foo", "bar");
		assertEquals("bar", area.getStringProperty("foo"));
	}
	
	public void testSetIntProperty() {
		UsrArea area = new UsrArea();
		
		assertEquals(0, area.getIntProperty("foo"));
		area.setIntProperty("foo", 123);
		assertEquals(123, area.getIntProperty("foo"));
	}

	public void testIntPropertyToString() {
		UsrArea area = new UsrArea();
		
		area.setIntProperty("foo", 123);
		assertEquals("<usr><foo dt='i4'>123</foo></usr>   ", area.toString());
	}
	
	public void testSetShortProperty() {
		UsrArea area = new UsrArea();
		
		assertEquals(0, area.getShortProperty("foo"));
		area.setShortProperty("foo", (short) 123);
		assertEquals(123, area.getShortProperty("foo"));
	}

	public void testShortPropertyToString() {
		UsrArea area = new UsrArea();
		
		area.setShortProperty("foo", (short) 123);
		assertEquals("<usr><foo dt='i2'>123</foo></usr>   ", area.toString());
	}
	
	public void testSetLongProperty() {
		UsrArea area = new UsrArea();
		
		assertEquals(0, area.getLongProperty("foo"));
		area.setLongProperty("foo", 123);
		assertEquals(123, area.getLongProperty("foo"));
	}

	public void testLongPropertyToString() {
		UsrArea area = new UsrArea();
		
		area.setLongProperty("foo", 123);
		assertEquals("<usr><foo dt='i8'>123</foo></usr>   ", area.toString());
	}
	
	public void testSetByteProperty() {
		UsrArea area = new UsrArea();
		
		assertEquals(0, area.getByteProperty("foo"));
		area.setByteProperty("foo", (byte) 2);
		assertEquals(2, area.getByteProperty("foo"));
	}

	public void testBytePropertyToString() {
		UsrArea area = new UsrArea();
		
		area.setByteProperty("foo", (byte) 2);
		assertEquals("<usr><foo dt='i1'>2</foo></usr> ", area.toString());
	}
	
	public void testSetBooleanProperty() {
		UsrArea area = new UsrArea();
		
		assertEquals(false, area.getBooleanProperty("foo"));
		area.setBooleanProperty("foo", true);
		assertEquals(true, area.getBooleanProperty("foo"));
	}

	public void testBooleanPropertyToString() {
		UsrArea area = new UsrArea();
		
		area.setBooleanProperty("foo", true);
		assertEquals("<usr><foo dt='boolean'>1</foo></usr>", area.toString().trim());

		area.setBooleanProperty("foo", false);
		assertEquals("<usr><foo dt='boolean'>0</foo></usr>", area.toString().trim());

	}
	
	public void testSetFloatProperty() {
		UsrArea area = new UsrArea();
		
		assertEquals(0f, area.getFloatProperty("foo"));
		area.setFloatProperty("foo", (float) 1.23);
		assertEquals(1.23f, area.getFloatProperty("foo"));
	}

	public void testFloatPropertyToString() {
		UsrArea area = new UsrArea();
		
		area.setFloatProperty("foo", (float) 1.23);
		assertEquals("<usr><foo dt='r4'>1.23</foo></usr>", area.toString().trim());
	}
	
	public void testSetDoubleProperty() {
		UsrArea area = new UsrArea();
		
		assertEquals(0.0, area.getDoubleProperty("foo"));
		area.setDoubleProperty("foo", 1.23);
		assertEquals(1.23, area.getDoubleProperty("foo"));
	}

	public void testDoublePropertyToString() {
		UsrArea area = new UsrArea();
		
		area.setDoubleProperty("foo", 1.23);
		assertEquals("<usr><foo dt='r8'>1.23</foo></usr>", area.toString().trim());
	}

	public void testStringPropertyNullToString() {
		UsrArea area = new UsrArea();
		
		area.setStringProperty("foo", null);
		assertEquals("<usr><foo xsi:nil='true'></foo></usr>", area.toString().trim());
	}
	
	public void testStringPropertyEscapedCharsToString() {
		UsrArea area = new UsrArea();
		
		area.setStringProperty("foo", "f<f&f>f");
		assertEquals("<usr><foo>f&lt;f&amp;f&gt;f</foo></usr>", area.toString().trim());
	}
}
