package com.sm.bff.ws.core;

import jakarta.websocket.Session;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class ConnectionContext {
    private final String userId;
    private final Session session;
    private final Map<String, Object> attributes;

    public ConnectionContext(String userId, Session session, Map<String, Object> attributes) {
        this.userId = userId;
        this.session = session;
        this.attributes = attributes != null ? attributes : new HashMap<>();
    }

    public Object getAttribute(String key) {
        return attributes.get(key);
    }

    public void setAttribute(String key, Object value) {
        attributes.put(key, value);
    }
}
