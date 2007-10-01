package com.googlecode.wmqutils.headers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ibm.mq.MQMessage;

public class MQRFH2 {

    private final static String RFH2_FORMAT = "MQHRF2  ";
    private final static String STRUC_ID = "RFH ";
    private final static int STRUC_LENGTH = 36;
    
    private int version = 2;
    private int encoding = 546;
    private int codedCharSetId = 437;
    private String format = "";
    private int flags = 0;
    private int nameValueCodedCharSetId = 1208;    
    
    private List<RFH2Area> areas = new ArrayList<RFH2Area>();

    public MQRFH2() {
        // default cstr
    }

    public MQRFH2(MQMessage msg) throws IOException {
        // create from message
        parseMessage(msg);
    }
    
    private void parseMessage(MQMessage msg) throws IOException {
        if (!RFH2_FORMAT.equals(msg.format)) {
            // no RFH2 header, skip
            return;
        }

        // skip strucid
        msg.seek(4);
        version = msg.readInt();
        int length = msg.readInt();
        encoding = msg.readInt();
        codedCharSetId = msg.readInt();
        format = msg.readString(8);
        flags = msg.readInt();
        nameValueCodedCharSetId = msg.readInt();
        
//         TODO: parse folders
    }
    
    public void toMessage(MQMessage msg) throws IOException {
        int areasLen = 0;
        List<String> areaStrings = new ArrayList<String>();
        for (RFH2Area area : areas) {
            
            String areaString = area.toString();
            
            // add to length for prepending length int
            areasLen += 4;
            areasLen += areaString.length();
            areaStrings.add(areaString);
        }
        
        msg.seek(0);

        msg.format = RFH2_FORMAT;
        
        msg.writeString(STRUC_ID);
        msg.writeInt(version);
        msg.writeInt(STRUC_LENGTH + areasLen);
        msg.writeInt(encoding);
        msg.writeInt(codedCharSetId);
        msg.writeString(pad(format, 8));
        msg.writeInt(flags);
        msg.writeInt(nameValueCodedCharSetId);

        for (String folderString : areaStrings) {
            msg.writeInt(folderString.length());
            msg.write(folderString.getBytes("UTF-8"));
        }
    }
    


    private String pad(String s, int len) {
        StringBuffer sb = new StringBuffer(s);
        for(int i = s.length(); i<len; i++) {
            sb.append(' ');
        }
        return sb.toString();
    }
    
    public void addArea(RFH2Area area) {
    	areas.add(area);
    }
    
    public RFH2Area getArea(String areaName) {
    	for(RFH2Area area : areas ) {
    		if(area.getAreaName().equals(areaName)) {
    			return area;
    		}
    	}
    	
    	// no area was found
    	return null;
    }
    
    public List<RFH2Area> getAllAreas() {
    	return areas;
    }
    
    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getEncoding() {
        return encoding;
    }

    public void setEncoding(int encoding) {
        this.encoding = encoding;
    }

    public int getCodedCharSetId() {
        return codedCharSetId;
    }

    public void setCodedCharSetId(int codedCharSetId) {
        this.codedCharSetId = codedCharSetId;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public int getFlags() {
        return flags;
    }

    public void setFlags(int flags) {
        this.flags = flags;
    }

    public int getNameValueCodedCharSetId() {
        return nameValueCodedCharSetId;
    }

    public void setNameValueCodedCharSetId(int nameValueCodedCharSetId) {
        this.nameValueCodedCharSetId = nameValueCodedCharSetId;
    }
}
