package com.googlecode.wmqutils.fileadapter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.googlecode.wmqutils.fileadapter.listener.DirectoryValidator;
import com.googlecode.wmqutils.fileadapter.producer.MessageProducer;


/**
 * Starts a JMS listener. The dir parameter needs to be set to where the
 * property files are placed: -Ddir=/file/path/
 * 
 */
public class FileAdapter {
    private static final Log logger = LogFactory.getLog(FileAdapter.class);

    /**
     * The main method initiates the spring context and starts the listener. We
     * also make sure that the target folder exists.
     * 
     * @param args no arguments
     */
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        logger.info("Spring context initialized. Listening...");
        DirectoryValidator cd = (DirectoryValidator) ctx.getBean("validateDirectoryBean");
        if (!cd.folderExists()) {
            logger.info("Application exits.");
            System.exit(1);
        }
        MessageProducer messageProducer = (MessageProducer) ctx.getBean("producer");
        messageProducer.start();
    }
}
