package com.sm.lib.service.redis;

import io.vertx.redis.client.Redis;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RedisService {
    private final Redis redis;
    private final RedisScript redisScript;

    @Inject
    public RedisService(Redis redis, RedisScript redisScript) {
        this.redis = redis;
        this.redisScript = redisScript;
    }

    public boolean singleRequest(String key, long ttl) {
        return redisScript.singleRequest(key, ttl);
    }

}
