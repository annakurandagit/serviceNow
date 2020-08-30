package com.interview.annak.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * FileReaderHandler
 * Input file handler
 */
public class FileReaderHandler {
    private static final Logger logger = LoggerFactory.getLogger(FileReaderHandler.class);
    private final String filePath;
    BufferedReader reader;

    public FileReaderHandler(String filePath) {
        this.filePath = filePath;
    }

    public void open() {
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        } catch (FileNotFoundException e) {
            logger.error(String.format("Failed get file reader %s, /n ,%s", filePath, e));
        }
    }

    public void close() {

        try {
            if (reader != null)
                reader.close();
        } catch (IOException e) {
            logger.error("File Reader close issue " + e);
        }
    }


    public BufferedReader getHandler() {
        return reader;
    }
}
