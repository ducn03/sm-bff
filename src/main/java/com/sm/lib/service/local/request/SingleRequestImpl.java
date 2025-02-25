package com.sm.lib.service.local.request;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.CustomLog;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

@ApplicationScoped
@CustomLog
public class SingleRequestImpl implements SingleRequest {
    private final ConcurrentHashMap<String, AtomicStampedReference<Long>> requestLocks = new ConcurrentHashMap<String, AtomicStampedReference<Long>>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    @Override
    public boolean singleRequest(String key, long ttl) {
        long startTime = System.nanoTime();

        AtomicStampedReference<Long> existingLock = requestLocks.get(key);
        int[] stampHolder = new int[1];

        if (existingLock != null) {
            long currentExpiry = existingLock.get(stampHolder);
            // Lock vẫn còn hiệu lực, từ chối request
            if (System.currentTimeMillis() < currentExpiry) {
                log.info("Request bị từ chối, còn hiệu lực: " + currentExpiry);
                long endTime = System.nanoTime() - startTime;
                log.info("Thời gian xử lý: " + endTime + " ns");
                log.info("Thời gian xử lý: " + ((float) (endTime) / (float) 1_000_000) + " ms");
                return false;
            }
        }

        long expiryTime = System.currentTimeMillis() + (ttl * 1000L);
        AtomicStampedReference<Long> newLock = new AtomicStampedReference<>(expiryTime, 0);
        requestLocks.put(key, newLock);

        scheduler.schedule(() -> requestLocks.remove(key, newLock), ttl, TimeUnit.SECONDS);
        long endTime = System.nanoTime() - startTime;
        log.info("Thời gian xử lý: " + endTime + " ns");
        log.info("Thời gian xử lý: " + ((float) (endTime) / (float) 1_000_000) + " ms");
        return true;
    }

    @Override
    public void delete(String key) {
        requestLocks.remove(key);
    }
}
