package com.sm.lib.service.local.cache;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class LocalCacheServiceImpl implements LocalCacheService{
    private final ConcurrentHashMap<String, CacheEntry<?>> cache = new ConcurrentHashMap<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    @Override
    public void set(String key, Object value, long ttl) {
        long expiryTime = System.currentTimeMillis() + (ttl * 1000L);
        cache.put(key, new CacheEntry<>(value, expiryTime));

        scheduler.schedule(() -> cache.remove(key), ttl, TimeUnit.SECONDS);
    }

    @Override
    public <T> T get(String key, Class<T> type) {
        CacheEntry<?> cacheEntry = cache.get(key);
        if (cacheEntry == null || System.currentTimeMillis() > cacheEntry.getExpiryTime()) {
            cache.remove(key);
            return null;
        }
        return type.cast(cacheEntry.getValue());
    }

    @Override
    public void delete(String key) {
        cache.remove(key);
    }
}
