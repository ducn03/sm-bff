package com.sm.lib.service.local.cache;

import lombok.Getter;

@Getter
public class CacheEntry<T> {
    private final T value;
    private final long expiryTime;

    public CacheEntry(T value, long expiryTime) {
        this.value = value;
        this.expiryTime = expiryTime;
    }
}
