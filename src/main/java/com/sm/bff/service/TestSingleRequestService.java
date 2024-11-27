package com.sm.bff.service;

import com.sm.lib.service.redis.RedisService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TestSingleRequestService {
    private final RedisService redisService;

    @Inject
    public TestSingleRequestService(RedisService redisService) {
        this.redisService = redisService;
    }

    public boolean isRequested(String key, long ttl){
        return redisService.singleRequest(key, ttl);
    }
}
