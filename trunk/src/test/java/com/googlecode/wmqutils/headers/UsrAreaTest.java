/*
 * Copyright 2007 (C) Callista Enterprise.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 *
 *	http://www.apache.org/licenses/LICENSE-2.0 
 *
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License.
 */

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
		
		assertEquals(0f, area.getFloatProperty("foo"), 0.1);
		area.setFloatProperty("foo", (float) 1.23);
		assertEquals(1.23f, area.getFloatProperty("foo"), 0.1);
	}

	public void testFloatPropertyToString() {
		UsrArea area = new UsrArea();
		
		area.setFloatProperty("foo", (float) 1.23);
		assertEquals("<usr><foo dt='r4'>1.23</foo></usr>", area.toString().trim());
	}
	
	public void testSetDoubleProperty() {
		UsrArea area = new UsrArea();
		
		assertEquals(0.0, area.getDoubleProperty("foo"), 0.1);
		area.setDoubleProperty("foo", 1.23);
		assertEquals(1.23, area.getDoubleProperty("foo"), 0.1);
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
		
		area.setStringProperty("foo", "f<f&f>f<f&f>f");
		assertEquals("<usr><foo>f&lt;f&amp;f&gt;f&lt;f&amp;f&gt;f</foo></usr>", area.toString().trim());
	}
	
	
	public void testParseBasic() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo>bar</foo><baz>bez</baz></usr>");
		assertEquals("bar", area.getStringProperty("foo"));
		assertEquals("bez", area.getStringProperty("baz"));
	}

	public void testParseWithEmptyArea() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr></usr>");
		assertEquals(0, area.getPropertyNames().length);
	}

	public void testParseWithEmptyTag() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo /></usr>");
		assertEquals("", area.getStringProperty("foo"));
		assertEquals("foo", area.getPropertyNames()[0]);
	}
	
	public void testParseWithNull() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo xsi:nil='true'></foo></usr>");
		assertTrue(area.containsProperty("foo"));
		assertNull(area.getStringProperty("foo"));
	}

	public void testParseMixed() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo /><fez>baz</fez></usr>");
		assertEquals("", area.getStringProperty("foo"));
		assertEquals("baz", area.getStringProperty("fez"));
	}

	public void testParseInt() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo dt='i4'>123</foo></usr>");
		assertEquals(123, area.getIntProperty("foo"));
		assertEquals("foo", area.getPropertyNames()[0]);
	}
	
	public void testParseLong() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo dt='i8'>123</foo></usr>");
		assertEquals(123l, area.getLongProperty("foo"));
	}
	
	public void testParseShort() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo dt='i2'>12</foo></usr>");
		assertEquals(12, area.getShortProperty("foo"));
	}
	
	public void testParseByte() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo dt='i1'>2</foo></usr>");
		assertEquals(2, area.getByteProperty("foo"));
	}
	
	public void testParseBoolean() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo dt='boolean'>1</foo><fez dt='boolean'>0</fez></usr>");
		assertEquals(true, area.getBooleanProperty("foo"));
		assertEquals(false, area.getBooleanProperty("fez"));
	}
	
	public void testParseFloat() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo dt='r4'>1.23</foo></usr>");
		assertEquals(1.23, area.getFloatProperty("foo"), 0.1);
	}
	
	public void testParseDouble() throws Exception {
		UsrArea area = (UsrArea) RFH2Area.parse("<usr><foo dt='r8'>1.23</foo></usr>");
		assertEquals(1.23, area.getDoubleProperty("foo"), 0.1);
	}
}
