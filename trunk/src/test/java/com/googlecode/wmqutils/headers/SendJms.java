package com.googlecode.wmqutils.headers;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import com.ibm.mq.jms.MQConnectionFactory;

public class SendJms {

	public static void main(String[] args) throws JMSException {
		MQConnectionFactory qcf = new MQConnectionFactory();
		qcf.setQueueManager("QM1");
		
		Connection conn = qcf.createConnection();
		Session session = conn.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Queue q = session.createQueue("Q1");
		
		MessageProducer producer = session.createProducer(q);
		StreamMessage msg = session.createStreamMessage();
		msg.setJMSCorrelationID("corrIdsdffffffffffffffffffffffffffffffffffffffffffffffffffff");
		msg.setJMSExpiration(3242442342243l);
		msg.setJMSReplyTo(q);
		msg.setIntProperty("int", 45);
		msg.setBooleanProperty("bo", false);
		msg.setBooleanProperty("bo2", true);
		msg.setByteProperty("by", (byte)3);
		msg.setDoubleProperty("double", 123.567);
		msg.setFloatProperty("fl", 123.789F);
		msg.setLongProperty("lo", 2423l);
		msg.setObjectProperty("ob", "foooo");
		msg.setObjectProperty("ob2", new Long(123));
		msg.setShortProperty("sh", (short) 23);
		
		
		producer.send(msg, DeliveryMode.PERSISTENT, 7, 34535535324l);
		
	}
}
