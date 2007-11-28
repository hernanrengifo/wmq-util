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

public class JmsAreaTest extends TestCase {

	public void testDestination() {
		JmsArea area = new JmsArea();
		
		assertNull(area.getDestination());
		
		area.setDestination("foo");
		assertEquals("foo", area.getDestination());
		
		area.setDestination(null);
		assertNull(area.getDestination());
	}

	public void testReplyTo() {
		JmsArea area = new JmsArea();
		
		assertNull(area.getReplyTo());
		
		area.setReplyTo("foo");
		assertEquals("foo", area.getReplyTo());
		
		area.setReplyTo(null);
		assertNull(area.getReplyTo());
	}
	
	public void testCorrelationId() {
		JmsArea area = new JmsArea();
		
		assertNull(area.getCorrelationId());
		
		area.setCorrelationId("foo");
		assertEquals("foo", area.getCorrelationId());
		
		area.setCorrelationId(null);
		assertNull(area.getCorrelationId());
	}
	
	public void testDeliveryMode() {
		JmsArea area = new JmsArea();
		
		assertEquals(0, area.getDeliveryMode());
		
		area.setDeliveryMode(4);
		assertEquals(4, area.getDeliveryMode());
	}	
	
	public void testPriority() {
		JmsArea area = new JmsArea();
		
		assertEquals(0, area.getPriority());
		
		area.setPriority(4);
		assertEquals(4, area.getPriority());
	}
	
	public void testTimestamp() {
		JmsArea area = new JmsArea();
		
		assertEquals(0, area.getTimestamp());
		
		area.setTimestamp(4);
		assertEquals(4, area.getTimestamp());
	}
	
	public void testExpiration() {
		JmsArea area = new JmsArea();
		
		assertEquals(0, area.getExpiration());
		
		area.setExpiration(4);
		assertEquals(4, area.getExpiration());
	}
	
	public void testToString() {
		JmsArea area = new JmsArea();
		area.setDestination("queue:///foo");
		area.setPriority(4);
		
		assertEquals("<jms><Dst>queue:///foo</Dst><Pri>4</Pri></jms>  ", area.toString());
	}
	
	
	public void testToStringCustomFolderName() {
		JmsArea area = new JmsArea("bar");
		area.setDestination("queue:///foo");
		area.setPriority(4);
		
		assertEquals("<bar><Dst>queue:///foo</Dst><Pri>4</Pri></bar>  ", area.toString());
	}
	
	public void testToStringWithNullValue() {
		JmsArea area = new JmsArea();
		area.setDestination("queue:///foo");
		area.setReplyTo("queue:///foo");
		area.setReplyTo(null);
		area.setPriority(4);
		
		assertEquals("<jms><Dst>queue:///foo</Dst><Pri>4</Pri></jms>  ", area.toString());
	}
}
