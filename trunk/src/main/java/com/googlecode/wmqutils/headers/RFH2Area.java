package com.googlecode.wmqutils.headers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class RFH2Area {

	private String areaName;
	protected Map<String, Object> properties = new HashMap<String, Object>();
	
	public RFH2Area(String areaName) {
		this.areaName = areaName;
	}

    private int getPaddedFolderLength(int headerLen)
    {
    	int rem = headerLen % 4; 
    	if(rem == 0) {
    		return headerLen;
    	} else {
    		return headerLen + (4- rem);
    	}
    }

    private StringBuffer padFolder(StringBuffer sb)
    {
        int folderLen = sb.length();
        int len = getPaddedFolderLength(folderLen);

        for(int i = folderLen; i<len; i++) {
            sb.append(' ');
        }
        return sb;
    }

	protected void setProperty(String name, Object value) {
		if(value == null) {
			properties.remove(name);
		} else {
			properties.put(name, value);
		}
	}


	protected int returnInt(String key, int defaultValue) {
		Object value = properties.get(key);
		
		if(value == null) {
			return defaultValue;
		} else {
			return ((Integer)value).intValue();
		}
	}

	protected long returnLong(String key, long defaultValue) {
		Object value = properties.get(key);
		
		if(value == null) {
			return defaultValue;
		} else {
			return ((Long)value).longValue();
		}
	}

	protected boolean returnBoolean(String key, boolean defaultValue) {
		Object value = properties.get(key);
		
		if(value == null) {
			return defaultValue;
		} else {
			return ((Boolean)value).booleanValue();
		}
	}
	
	protected byte returnByte(String key, byte defaultValue) {
		Object value = properties.get(key);
		
		if(value == null) {
			return defaultValue;
		} else {
			return ((Byte)value).byteValue();
		}
	}

	protected short returnShort(String key, short defaultValue) {
		Object value = properties.get(key);
		
		if(value == null) {
			return defaultValue;
		} else {
			return ((Short)value).shortValue();
		}
	}
	
	protected float returnFloat(String key, float defaultValue) {
		Object value = properties.get(key);
		
		if(value == null) {
			return defaultValue;
		} else {
			return ((Float)value).floatValue();
		}
	}
	protected double returnDouble(String key, double defaultValue) {
		Object value = properties.get(key);
		
		if(value == null) {
			return defaultValue;
		} else {
			return ((Double)value).doubleValue();
		}
	}
	
	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<").append(areaName).append(">");
        
        for (Entry<String, Object> entry : properties.entrySet()) {
            sb.append("<").append(entry.getKey());
            
            Object value = entry.getValue();
            
            if(value instanceof String) {
            	sb.append(">").append(value);
            } else if(value instanceof Integer) {
            	sb.append(" dt='i4'>").append(value);
            } else if(value instanceof Long) {
            	sb.append(" dt='i8'>").append(value);
            } else if(value instanceof Short) {
            	sb.append(" dt='i2'>").append(value);
            } else if(value instanceof Byte) {
            	sb.append(" dt='i1'>").append(value);
            } else if(value instanceof Float) {
            	sb.append(" dt='r4'>").append(value);
            } else if(value instanceof Double) {
            	sb.append(" dt='r8'>").append(value);
            } else if(value instanceof Boolean) {
            	sb.append(" dt='boolean'>");
            	if(((Boolean)value).booleanValue()) {
            		sb.append(1);
            	} else {
            		sb.append(0);
            	}
            } else {
            	throw new RuntimeException("Illegal proprty type:" + value);
            }
            
            sb.append("</").append(entry.getKey()).append(">");
        }
        
        sb.append("</").append(areaName).append(">");
        return padFolder(sb).toString();
	
	}
}
