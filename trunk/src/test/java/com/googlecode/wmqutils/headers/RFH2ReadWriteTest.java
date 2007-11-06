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

import com.ibm.mq.MQMessage;

public class RFH2ReadWriteTest extends TestCase {

    public void testWriteRead() throws Exception {
        MQMessage msg = new MQMessage();
        MQRFH2 rfh2 = new MQRFH2();
        
        McdArea mcd = new McdArea();
        mcd.setMessageDomain("MRM");
        rfh2.addArea(mcd);
        
        UsrArea usr = new UsrArea();
        usr.setStringProperty("kalle", "rune");
        usr.setStringProperty("i18n", "едц");
        usr.setIntProperty("int", 123);
        usr.setBooleanProperty("bool", true);
        usr.setDoubleProperty("d", 1.23);
        usr.setFloatProperty("f", 1.23f);
        usr.setShortProperty("s", (short) 12);
        usr.setLongProperty("l", 123);
        usr.setByteProperty("b", (byte) 2);
        rfh2.addArea(usr);
        
        rfh2.toMessage(msg);
        
        msg.seek(0);

        MQRFH2 recievedRfh2 = new MQRFH2(msg);
        
        McdArea mcd2 = (McdArea) recievedRfh2.getArea("mcd");
        assertEquals("MRM", mcd2.getMessageDomain());

        
        UsrArea usr2 = (UsrArea) recievedRfh2.getArea("usr");
        assertEquals("rune", usr2.getStringProperty("kalle"));
        assertEquals("едц", usr2.getStringProperty("i18n"));
        assertEquals(123, usr2.getIntProperty("int"));
        assertEquals(true, usr2.getBooleanProperty("bool"));
        assertEquals(1.23, usr2.getDoubleProperty("d"), 0.1);
        assertEquals(1.23, usr2.getFloatProperty("f"), 0.1);
        assertEquals(12, usr2.getShortProperty("s"));
        assertEquals(123, usr2.getLongProperty("l"));
        assertEquals(2, usr2.getByteProperty("b"));
    }
}
