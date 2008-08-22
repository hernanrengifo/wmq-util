package com.googlecode.wmqutils.fileadapter.listener;

import java.io.File;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class DirectoryValidator {
    private static final Log logger = LogFactory.getLog(DirectoryValidator.class);
    private String targetDirectory;

    public void setTargetDirectory(String targetDirectory) {
        this.targetDirectory = targetDirectory;
    }

    public String getTargetDirectory() {
        return targetDirectory;
    }

    public boolean folderExists() {
        File dir = new File(targetDirectory);
        if (dir.exists() && dir.isDirectory()) {
            logger.info("Writing incoming files to " + targetDirectory);
            return true;
        } else {
            logger.error("Directory does not exist: " + targetDirectory);
            return false;
        }
    }
}
