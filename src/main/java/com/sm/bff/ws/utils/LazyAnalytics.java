package com.sm.bff.ws.utils;

public class LazyAnalytics {
    private final long timestamp;
    private final int connectionCount;

    public LazyAnalytics(int connectionCount) {
        this.timestamp = System.currentTimeMillis();
        this.connectionCount = connectionCount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public int getConnectionCount() {
        return connectionCount;
    }
}
