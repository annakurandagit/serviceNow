package com.interview.annak.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * FileWriterHandler
 * Output file handler
 */
public class FileWriterHandler {

    private static final Logger logger = LoggerFactory.getLogger(FileWriterHandler.class);
    private final String filePath;
    private FileOutputStream outputStream;

    public FileWriterHandler(String filePath) {
        this.filePath = filePath;
    }

    public void open() {
        try {
            File path = new File(filePath);
            if (path.exists()) {
                path.delete();
            }

            path.createNewFile();

            outputStream = new FileOutputStream(filePath);
        } catch (FileNotFoundException e) {
            logger.error(String.format("Failed get file writer %s, /n ,%s", filePath, e));
        } catch (IOException e) {
            logger.error(String.format("Failed create file  %s, /n ,%s", filePath, e));
        }
    }

    public void close() {
        try {
            outputStream.close();
        } catch (IOException e) {
            logger.error("File Reader close issue " + e);
        }
    }


    public OutputStream getHandler() {
        return outputStream;
    }


}
