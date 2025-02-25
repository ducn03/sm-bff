package com.sm.lib.service.local.request;

public interface SingleRequest {
    boolean singleRequest(String key, long ttl);

    void delete(String key);
}
