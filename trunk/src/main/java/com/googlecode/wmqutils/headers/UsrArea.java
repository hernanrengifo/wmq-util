package com.googlecode.wmqutils.headers;

public class UsrArea extends RFH2Area {

	public UsrArea() {
		super("usr");
	}
	
	public UsrArea(String areaName) {
		super(areaName);
	}
	
	public String getStringProperty(String name) {
		Object value = properties.get(name);
		
		if(value == null) {
			return null;
		} else {
			return value.toString();
		}
	}
	
	public void setStringProperty(String name, String value) {
		properties.put(name, value);
	}

	public int getIntProperty(String name) {
		return returnInt(name, 0);
	}

	public void setIntProperty(String name, int value) {
		setProperty(name, Integer.valueOf(value));
	}

	public long getLongProperty(String name) {
		return returnLong(name, 0);
	}

	public void setLongProperty(String name, long value) {
		setProperty(name, Long.valueOf(value));
	}	

	public short getShortProperty(String name) {
		return returnShort(name, (short) 0);
	}

	public void setShortProperty(String name, short value) {
		setProperty(name, Short.valueOf(value));
	}
	

	public byte getByteProperty(String name) {
		return returnByte(name, (byte) 0);
	}

	public void setByteProperty(String name, byte value) {
		setProperty(name, Byte.valueOf(value));
	}
	

	public float getFloatProperty(String name) {
		return returnFloat(name, 0f);
	}

	public void setFloatProperty(String name, float value) {
		setProperty(name, Float.valueOf(value));
	}
	

	public double getDoubleProperty(String name) {
		return returnDouble(name, 0d);
	}

	public void setDoubleProperty(String name, double value) {
		setProperty(name, Double.valueOf(value));
	}
	

	public boolean getBooleanProperty(String name) {
		return returnBoolean(name, false);
	}

	public void setBooleanProperty(String name, boolean value) {
		setProperty(name, Boolean.valueOf(value));
	}
}
