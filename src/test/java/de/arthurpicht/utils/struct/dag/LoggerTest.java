package de.arthurpicht.utils.struct.dag;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerTest {

    @BeforeAll
    static void prepare() {
//        System.setProperty("org.slf4j.simpleLogger.defaultLogLevel", "trace");
//        System.setProperty("org.slf4j.simpleLogger.showThreadName", "false");
//        System.setProperty("org.slf4j.simpleLogger.showLogName", "false");
    }

    @Test
    void test() {

        Logger logger = LoggerFactory.getLogger(LoggerTest.class);
        logger.trace("MESSAGE!");

    }

}
