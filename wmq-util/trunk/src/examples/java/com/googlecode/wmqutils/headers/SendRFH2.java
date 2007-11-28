package com.googlecode.wmqutils.headers;

import com.googlecode.wmqutils.headers.MQRFH2;
import com.ibm.mq.MQC;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class SendRFH2 {

	private static final String QUEUE_MANAGER = "QM1";
	private static final String QUEUE 		  = "Q1";
	
    public static void main(String[] args) throws Exception {
        MQQueueManager qm = new MQQueueManager(QUEUE_MANAGER);
        int oo = MQC.MQOO_OUTPUT;
        MQQueue q = qm.accessQueue(QUEUE, oo);
        
        MQMessage msg = new MQMessage();
        MQRFH2 rfh2 = new MQRFH2();
        
        McdArea mcd = new McdArea();
        mcd.setMessageDomain("MRM");
        rfh2.addArea(mcd);
        
        UsrArea usr = new UsrArea();
        usr.setStringProperty("i18n", "едц");
        rfh2.addArea(usr);
        
        rfh2.toMessage(msg);
        q.put(msg);
    }
}
