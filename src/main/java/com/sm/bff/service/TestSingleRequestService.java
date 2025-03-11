package com.sm.bff.service;

import com.sm.lib.helper.JsonHelper;
import com.sm.lib.service.redis.Redis;
import com.sm.lib.service.local.request.SingleRequest;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.CustomLog;

@ApplicationScoped
@CustomLog
public class TestSingleRequestService {
    private final Redis redis;
    private final SingleRequest singleRequest;

    @Inject
    public TestSingleRequestService(Redis redis, SingleRequest singleRequest) {
        this.redis = redis;
        this.singleRequest = singleRequest;
    }

    public Uni<Boolean> isRequested(String key, long ttl){
        // runExample();
        log.trace("result: " + singleRequest.singleRequest(key, ttl));
        return redis.singleRequest(key, ttl);
    }

    public void runExample() {
        // Set key-value với TTL
        redis.set("set", "data", 100);
        // Set key-value không TTL
        redis.set("set-no-ttl", "no ttl");
        redis.set("set-no-ttl-2", "no ttl");
        // Delete key
        redis.delete("set-no-ttl-2");
        // HSet
        redis.hSet("hset", "1", "content 1");
        redis.hSet("hset", "2", "content 2");
        redis.hSet("hset", "3", "content 3");
        // HGet
        redis.hGet("hset", "1")
                .subscribe().with(
                        value -> System.out.println("HGet result for key 'hset', field '1': " + value),
                        failure -> System.err.println("Failed to perform HGet: " + failure.getMessage())
                );
        // HGetAll
        redis.hGetAll("hset")
                .subscribe().with(
                        map -> System.out.println("HGetAll result for key 'hset': " + JsonHelper.toJson(map)),
                        failure -> System.err.println("Failed to perform HGetAll: " + failure.getMessage())
                );
        redis.hDelete("hset", "3");

        // increment
        redis.increment("increment");
        redis.increment("increment");
        redis.increment("increment");

        // ZSet: Leaderboard Example
        String leaderboardKey = "leaderboard";

        // Add scores to the leaderboard
        redis.zAdd(leaderboardKey, 100, "Alice");
        redis.zAdd(leaderboardKey, 200, "Bob");
        redis.zAdd(leaderboardKey, 150, "Charlie");

        // Increment score for a member
        redis.zIncrement(leaderboardKey, 50, "Alice");

        // Decrement score for a member
        redis.ZDecrement(leaderboardKey, 30, "Bob");

        // Get rank (ascending order)
        redis.zRank(leaderboardKey, "Alice")
                .subscribe().with(
                        rank -> System.out.println("ZRank result for 'Alice': " + rank),
                        failure -> System.err.println("Failed to perform ZRank: " + failure.getMessage())
                );

        // Get reverse rank (descending order)
        redis.zRevRank(leaderboardKey, "Alice")
                .subscribe().with(
                        rank -> System.out.println("ZRevRank result for 'Alice': " + rank),
                        failure -> System.err.println("Failed to perform ZRevRank: " + failure.getMessage())
                );

        // Get leaderboard (ascending order with scores)
        redis.zRangeWithScores(leaderboardKey, 0, -1)
                .subscribe().with(
                        map -> System.out.println("ZRangeWithScores result: " + JsonHelper.toJson(map)),
                        failure -> System.err.println("Failed to perform ZRangeWithScores: " + failure.getMessage())
                );

        // Get leaderboard (descending order with scores)
        redis.zRevRangeWithScores(leaderboardKey, 0, -1)
                .subscribe().with(
                        map -> System.out.println("ZRevRangeWithScores result: " + JsonHelper.toJson(map)),
                        failure -> System.err.println("Failed to perform ZRevRangeWithScores: " + failure.getMessage())
                );

        // Remove a member from leaderboard
        redis.zRemove(leaderboardKey, "Charlie");

        // Set expiration time for the leaderboard
        redis.setExpire(leaderboardKey, 100);
    }
}
