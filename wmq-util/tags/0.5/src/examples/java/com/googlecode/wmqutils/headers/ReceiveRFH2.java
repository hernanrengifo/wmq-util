package com.googlecode.wmqutils.headers;

import com.googlecode.wmqutils.headers.MQRFH2;
import com.ibm.mq.MQC;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class ReceiveRFH2 {

	private static final String QUEUE_MANAGER = "QM1";
	private static final String QUEUE 		  = "Q1";
	
    public static void main(String[] args) throws Exception {
        MQQueueManager qm = new MQQueueManager(QUEUE_MANAGER);
        int oo = MQC.MQOO_INPUT_SHARED;
        MQQueue q = qm.accessQueue(QUEUE, oo);
        
        MQMessage msg = new MQMessage();
        q.get(msg);
        
        MQRFH2 rfh2 = new MQRFH2(msg);
        
        UsrArea usr = (UsrArea) rfh2.getArea("usr");
        if(usr != null) {
        	String[] propNames = usr.getPropertyNames();
        	
        	for(int i = 0; i<propNames.length; i++) {
        		System.out.println("Property " + propNames[i] + 
        				" found with value " + usr.getStringProperty(propNames[i]));
        	}
        }
        
    }
}
