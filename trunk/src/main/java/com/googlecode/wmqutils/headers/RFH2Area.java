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

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public abstract class RFH2Area {

	private String areaName;
	protected Map properties = new HashMap();
	
	public RFH2Area(String areaName) {
		this.areaName = areaName;
	}

    public String getAreaName() {
		return areaName;
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

    protected StringBuffer padFolder(StringBuffer sb)
    {
        int folderLen;
		try {
			folderLen = sb.toString().getBytes("UTF-8").length;
	        int len = getPaddedFolderLength(folderLen);

	        for(int i = folderLen; i<len; i++) {
	            sb.append(' ');
	        }
	        return sb;
		} catch (UnsupportedEncodingException e) {
			// should not happen
			return null;
		}

    }

	protected void setProperty(String name, Object value) {
		properties.put(name, value);
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
	
	protected String escapeValue(String s) {
		return s.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
	}
	
	protected boolean includeNullValueInToString() {
		return true;
	}
	
	
 	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<").append(areaName).append(">");
        
        propertiesToString(sb);
        
        sb.append("</").append(areaName).append(">");
        return padFolder(sb).toString();
	}

	protected void propertiesToString(StringBuffer sb) {
		Iterator propsIter = properties.entrySet().iterator();
        while (propsIter.hasNext()) {
        	Entry entry = (Entry) propsIter.next();
        	Object value = entry.getValue();
            if(value != null || includeNullValueInToString()) {
        	
	        	sb.append("<").append(entry.getKey());
	            
	            if(value instanceof String) {
	            	sb.append(">").append(escapeValue(value.toString()));
	            } else if(value == null) {
	            	sb.append(" xsi:nil='true'>");
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
	            	throw new RuntimeException("Illegal property type:" + value);
	            }
	            
	            sb.append("</").append(entry.getKey()).append(">");
            }
        }
	}


 	
 	public static RFH2Area parse(String stringToParse) throws Exception {
 		Rfh2AreaParser parser = new Rfh2AreaParser();
 		
 		return parser.parse(stringToParse);
 	}
}
