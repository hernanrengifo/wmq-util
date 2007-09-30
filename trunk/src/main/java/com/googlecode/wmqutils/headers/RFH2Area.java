package com.googlecode.wmqutils.headers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public abstract class RFH2Area {

	private String folderName;
	protected Map<String, Object> properties = new HashMap<String, Object>();
	
	public RFH2Area(String folderName) {
		this.folderName = folderName;
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



	public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("<").append(folderName).append(">");
        
        for (Entry<String, Object> entry : properties.entrySet()) {
            sb.append("<").append(entry.getKey()).append(">");
            sb.append(entry.getValue().toString());
            sb.append("</").append(entry.getKey()).append(">");
        }
        
        sb.append("</").append(folderName).append(">");
        return padFolder(sb).toString();
	
	}
}
