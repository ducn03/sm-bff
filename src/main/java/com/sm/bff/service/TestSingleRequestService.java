package com.sm.bff.service;

import com.sm.lib.helper.JsonHelper;
import com.sm.lib.service.redis.RedisService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;

@ApplicationScoped
public class TestSingleRequestService {
    private final RedisService redisService;

    @Inject
    public TestSingleRequestService(RedisService redisService) {
        this.redisService = redisService;
    }

    public Uni<Map<String, Object>> isRequested(String key, long ttl){
        // runExample();
        return redisService.hGetAll("hset");
    }

    public void runExample() {
        // Set key-value với TTL
        redisService.set("set", "data", 100);
        // Set key-value không TTL
        redisService.set("set-no-ttl", "no ttl");
        redisService.set("set-no-ttl-2", "no ttl");
        // Delete key
        redisService.delete("set-no-ttl-2");
        // HSet
        redisService.hSet("hset", "1", "content 1");
        redisService.hSet("hset", "2", "content 2");
        redisService.hSet("hset", "3", "content 3");
        // HGet
        redisService.hGet("hset", "1")
                .subscribe().with(
                        value -> System.out.println("HGet result for key 'hset', field '1': " + value),
                        failure -> System.err.println("Failed to perform HGet: " + failure.getMessage())
                );
        // HGetAll
        redisService.hGetAll("hset")
                .subscribe().with(
                        map -> System.out.println("HGetAll result for key 'hset': " + JsonHelper.toJson(map)),
                        failure -> System.err.println("Failed to perform HGetAll: " + failure.getMessage())
                );
        redisService.hDelete("hset", "3");
    }
}
