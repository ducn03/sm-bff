package com.sm.bff.config;

import io.vertx.ext.web.handler.BodyHandler;

public class BodyHandlerConfig {
    public static BodyHandler createBodyHandler() {
        return BodyHandler.create()
                .setBodyLimit(50 * 1024 * 1024)
                .setHandleFileUploads(true)
                .setUploadsDirectory("uploads");
    }
}
