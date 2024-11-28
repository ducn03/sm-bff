package com.sm.lib.logging;

import jakarta.inject.Singleton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Singleton
public class SMLogger implements ILogger {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass().getName());

    private static SMLogger logger;
    public static SMLogger getInstance() {
        if (logger != null) {
            return logger;
        }
        return logger = new SMLogger();
    }

    @Override
    public void info(String message) {
        if (message == null || message.isEmpty()) {
            return;
        }
        LOG.info(message);
    }

    @Override
    public void error(String message, Throwable e) {
        LOG.error(message, e);
    }
}
