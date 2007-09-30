package com.googlecode.wmqutils.headers;

import com.googlecode.wmqutils.headers.MQRFH2;
import com.ibm.mq.MQC;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

public class TestRFH2 {

    public static void main(String[] args) throws Exception {
        MQQueueManager qm = new MQQueueManager("QM1");
        int oo = MQC.MQOO_OUTPUT + MQC.MQOO_INPUT_SHARED;
        MQQueue q = qm.accessQueue("Q1", oo);
        
        MQMessage msg = new MQMessage();
        MQRFH2 rfh2 = new MQRFH2();
        UsrArea usr = new UsrArea();
        usr.setStringProperty("kalle", "rune");
        rfh2.addArea("usr", usr);
        rfh2.toMessage(msg);
        q.put(msg);
    }
}
