package com.googlecode.wmqutils.fileadapter.listener;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * This is the JMS message listener that receives the messages.
 * 
 */
public class Listener implements MessageListener {

    private String writeDirectory;
    private String filePrefix;
    private String fileSuffix;

    private static final Log logger = LogFactory.getLog(Listener.class);
    private static final Log msgLogger = LogFactory.getLog("incoming");

    /**
     * The message is received and written to the output directory.
     * 
     * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
     */
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            try {
                TextMessage txtMessage = (TextMessage) message;
                this.writeStringToFile(txtMessage.getText());
            } catch (JMSException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            String msg = "Message must be of type TextMessage. Make sure the MQSTR format is set in WMQ.";
            logger.error(msg);
            throw new IllegalArgumentException(msg);
        }
    }

    private void writeStringToFile(String text) {
        // File name is composed of the path a file prefix and a date-time.
        SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmssSSS");
        String filename = this.writeDirectory + File.separator + this.filePrefix
                + sdf.format(new Date()) + "." + this.fileSuffix;
        BufferedWriter out;
        boolean error = false;
        try {
            out = new BufferedWriter(new FileWriter(filename));
            out.write(text);
            out.close();
        } catch (IOException e) {
            logger.error("Could not write message to file. ", e);
            msgLogger.error("[NOFILE]\n" + text);
            error = true;
        }
        if (!error) {
            logger.info("Message received and written to file: " + filename);
            msgLogger.info("[" + filename + "]\n" + text);
        }
    }

    public void setWriteDirectory(String writeDirectory) {
        this.writeDirectory = writeDirectory;
    }

    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }
}
