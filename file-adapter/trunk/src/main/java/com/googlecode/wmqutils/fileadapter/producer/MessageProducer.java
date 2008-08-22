package com.googlecode.wmqutils.fileadapter.producer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Polls a directory with an interval specified in the property file. The
 * adapter also keeps the timestamp for last check persisted in a property file.
 * The adapter gets its properties from the file bridge.properties.
 * 
 */
public class MessageProducer extends Producer {
    private static final Log logger = LogFactory.getLog(MessageProducer.class);
    private static final Log msgLogger = LogFactory.getLog("outgoing");

    private final static SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private final static String PROPERTYFILE = "adapter.properties";
    private final static String LASTCHECK = "lastcheck.properties";

    /**
     * Main method of the file adapter. Contains the polling loop.
     */
    public void start() {
        // Initializing all properties
        Properties props = this.readProperties(PROPERTYFILE);
        Properties lastcheckProperties = this.readProperties(LASTCHECK);
        File dir = this.getDirectory(props);
        long lastCheck = this.getLastCheck(lastcheckProperties);
        long pause = Long.parseLong(props.getProperty("read.pause"));

        // Eternal polling loop. Polls every -pause- milliseconds
        while (true) {
            File[] files = dir.listFiles();
            Date now = new Date();
            for (int i = 0; i < files.length; i++) {
                if (files[i].lastModified() > lastCheck) {
                    // Process the new file
                    this.processFile(files[i]);
                }
            }

            // Persist the timestamp for last check
            lastCheck = now.getTime();
            this.saveProperties(lastcheckProperties, LASTCHECK, now);
            try {
                // sleep for -pause- milliseconds
                Thread.sleep(pause);
            } catch (InterruptedException e) {
                logger.error("FileAdapter exits.", e);
                System.exit(1);
            }
        }
    }

    private void processFile(File file) {
        logger.info("Discovered new file: " + file.getAbsolutePath());
        // First tries to rename file. If file still is being written this will
        // fail
        // and we will try again three times.
        /*
         * File newFile = new File("R" + file.getAbsolutePath()); int tries = 0;
         * boolean success = false; while (!success && tries < 3) { if (tries >
         * 0) { try { logger.error(file.getAbsolutePath() + "Renaming file
         * failed. " + (tries + 1) + " attempt."); Thread.sleep(1000 * tries *
         * tries); } catch (InterruptedException e) { logger.error("FileAdapter
         * exits.", e); System.exit(1); } } try { success =
         * file.renameTo(newFile); } catch (Exception e) {
         * logger.error(file.getAbsolutePath() + "Renaming file failed. " +
         * (tries + 1) + " attempt."); } tries++; }
         */
        // Read the file to a StringBuffer and then send the message
        try {
            FileInputStream fin = new FileInputStream(file);
            BufferedInputStream bin = new BufferedInputStream(fin);
            int ch = 0;
            StringBuffer buffer = new StringBuffer();
            while ((ch = bin.read()) > -1) {
                buffer.append((char) ch);
            }
            bin.close();
            fin.close();
            // Send file to queue
            this.sendStringMessage(buffer.toString());
            msgLogger.info("[" + file.getAbsolutePath() + "]\n" + buffer.toString());
        } catch (IOException e) {
            logger.error("Could not read file: " + file.getAbsolutePath(), e);
        }
    }

    private Properties readProperties(String file) {
        Properties props = new Properties();
        try {
            FileInputStream fis = new FileInputStream(file);
            props.load(fis);
            fis.close();
        } catch (Exception e) {
            logger.error("Could not read properties file: " + file);
            System.exit(1);
        }
        return props;
    }

    private void saveProperties(Properties props, String filename, Date now) {
        props.setProperty("lastcheck", SDF.format(now));
        try {
            FileOutputStream fos = new FileOutputStream("lastcheck.properties");
            props.store(fos, null);
            fos.close();
        } catch (IOException e) {
            logger
                    .error("Could not write properties file. Exiting. Last Check: "
                            + SDF.format(now));
            System.exit(1);
        }
    }

    private File getDirectory(Properties props) {
        String dirProperty = props.getProperty("read.directory");
        File dir = new File(dirProperty);
        if (!dir.exists()) {
            logger.error("Directory does not exist: " + dirProperty);
            System.exit(1);
        }
        if (!dir.isDirectory()) {
            logger.error("Can't read file from non-directory");
            System.exit(1);
        }
        logger.info("Polling directory is: " + dir.getAbsolutePath());
        return dir;
    }

    private long getLastCheck(Properties props) {
        String property = props.getProperty("lastcheck");
        Date lastCheckedDate = null;
        try {
            lastCheckedDate = SDF.parse(property);
        } catch (ParseException e) {
            logger.error("Could not read date from last check");
            System.exit(1);
        }
        long lastCheck = lastCheckedDate.getTime();
        logger.info("Last check: " + property);
        return lastCheck;
    }
}
