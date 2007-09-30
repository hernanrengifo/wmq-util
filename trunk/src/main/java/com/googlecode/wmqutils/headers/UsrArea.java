package com.googlecode.wmqutils.headers;

public class UsrArea extends RFH2Area {

	public UsrArea() {
		super("usr");
	}
	
	public String getStringProperty(String name) {
		return properties.get(name).toString();
	}
	
	public void setStringProperty(String name, String value) {
		properties.put(name, value);
	}

}
