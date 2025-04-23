package com.sm.bff.ws.core;

import jakarta.websocket.Session;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Quản lý session toàn cục
 */
public class SessionRegistry {
    private final Map<String, ConnectionContext> sessions = new ConcurrentHashMap<>();

    public void register(String userId, Session session, Map<String, Object> attributes) {
        ConnectionContext context = new ConnectionContext(userId, session, attributes);
        sessions.put(userId, context);
    }

    public void unregister(String userId) {
        sessions.remove(userId);
    }

    public ConnectionContext getContext(String userId) {
        return sessions.get(userId);
    }

    public boolean isConnected(String userId) {
        return sessions.containsKey(userId);
    }

    public int getActiveConnections() {
        return sessions.size();
    }

    public Set<Session> getAllSessions() {
        return sessions.values().stream()
                .map(ConnectionContext::getSession)
                .collect(Collectors.toSet());
    }

}
