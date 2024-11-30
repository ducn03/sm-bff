package com.sm.bff.service;

import com.sm.lib.helper.JsonHelper;
import com.sm.lib.service.redis.RedisService;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class TestSingleRequestService {
    private final RedisService redisService;

    @Inject
    public TestSingleRequestService(RedisService redisService) {
        this.redisService = redisService;
    }

    public Uni<Boolean> isRequested(String key, long ttl){
        runExample();
        return redisService.singleRequest(key, ttl);
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

        // ZSet: Leaderboard Example
        String leaderboardKey = "leaderboard";

        // Add scores to the leaderboard
        redisService.zAdd(leaderboardKey, 100, "Alice");
        redisService.zAdd(leaderboardKey, 200, "Bob");
        redisService.zAdd(leaderboardKey, 150, "Charlie");

        // Increment score for a member
        redisService.zIncrement(leaderboardKey, 50, "Alice");

        // Decrement score for a member
        redisService.ZDecrement(leaderboardKey, 30, "Bob");

        // Get rank (ascending order)
        redisService.zRank(leaderboardKey, "Alice")
                .subscribe().with(
                        rank -> System.out.println("ZRank result for 'Alice': " + rank),
                        failure -> System.err.println("Failed to perform ZRank: " + failure.getMessage())
                );

        // Get reverse rank (descending order)
        redisService.zRevRank(leaderboardKey, "Alice")
                .subscribe().with(
                        rank -> System.out.println("ZRevRank result for 'Alice': " + rank),
                        failure -> System.err.println("Failed to perform ZRevRank: " + failure.getMessage())
                );

        // Get leaderboard (ascending order with scores)
        redisService.zRangeWithScores(leaderboardKey, 0, -1)
                .subscribe().with(
                        map -> System.out.println("ZRangeWithScores result: " + JsonHelper.toJson(map)),
                        failure -> System.err.println("Failed to perform ZRangeWithScores: " + failure.getMessage())
                );

        // Get leaderboard (descending order with scores)
        redisService.zRevRangeWithScores(leaderboardKey, 0, -1)
                .subscribe().with(
                        map -> System.out.println("ZRevRangeWithScores result: " + JsonHelper.toJson(map)),
                        failure -> System.err.println("Failed to perform ZRevRangeWithScores: " + failure.getMessage())
                );

        // Remove a member from leaderboard
        redisService.zRemove(leaderboardKey, "Charlie");

        // Set expiration time for the leaderboard
        redisService.setExpire(leaderboardKey, 100);
    }
}
