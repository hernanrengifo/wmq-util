package com.googlecode.wmqutils.fileadapter.producer;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class Producer {
    private JmsTemplate jmsTemplate;
    private Queue destination;

    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public JmsTemplate getJmsTemplate() {
        return jmsTemplate;
    }

    public void setDestination(Queue destination) {
        this.destination = destination;
    }

    public Queue getDestination() {
        return destination;
    }

    public void sendStringMessage(final String message) {
        this.jmsTemplate.send(this.destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }
}
