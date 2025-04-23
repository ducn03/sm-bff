package com.sm.bff.ws.core;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WebSocketManager {
    private static WebSocketManager instance;
    private final SessionRegistry sessionRegistry;

    private WebSocketManager() {
        this.sessionRegistry = new SessionRegistry();
    }

    public static synchronized WebSocketManager getInstance() {
        if (instance == null) {
            instance = new WebSocketManager();
        }
        return instance;
    }

    public SessionRegistry getSessionRegistry() {
        return sessionRegistry;
    }

    // Các phương thức quản lý khác

}
