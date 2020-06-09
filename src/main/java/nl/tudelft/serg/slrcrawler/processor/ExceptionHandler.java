package nl.tudelft.serg.slrcrawler.processor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ExceptionHandler {

    private static final Logger logger = LogManager.getLogger(ExceptionHandler.class);

    public void handle(Exception e) {
        logger.error("e");
    }
}
