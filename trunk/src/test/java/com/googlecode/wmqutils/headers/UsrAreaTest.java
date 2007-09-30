package com.googlecode.wmqutils.headers;

import junit.framework.TestCase;

public class UsrAreaTest extends TestCase {

	public void testMatchingLength() {
		UsrArea area = new UsrArea();
		area.setStringProperty("foo", "ba");
		System.out.println(area.toString().length());
		assertEquals("<usr><foo>ba</foo></usr>", area.toString());
	}

	public void testOneOverLength() {
		UsrArea area = new UsrArea();
		area.setStringProperty("foo", "bar");
		System.out.println(area.toString().length());
		assertEquals("<usr><foo>bar</foo></usr>   ", area.toString());
	}

	public void testTwoOverLength() {
		UsrArea area = new UsrArea();
		area.setStringProperty("foo", "barr");
		System.out.println(area.toString().length());
		assertEquals("<usr><foo>barr</foo></usr>  ", area.toString());
	}

	public void testThreeOverLength() {
		UsrArea area = new UsrArea();
		area.setStringProperty("foo", "barrr");
		System.out.println(area.toString().length());
		assertEquals("<usr><foo>barrr</foo></usr> ", area.toString());
	}

}
