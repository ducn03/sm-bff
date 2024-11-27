package com.sm.lib.service;

import com.sm.lib.config.RedisConfig;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.RedisClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RedisService {
    private final Redis redisClient;
    private final String singleRequestScript;
    private final String SUCCESS = "1";

    @Inject
    public RedisService(RedisConfig redisConfig) {
        this.redisClient = redisConfig.getRedisClient();
        this.singleRequestScript = redisConfig.getSingleRequestScript();
    }

    public boolean singleRequest(String key, long ttl) {
        try {
            // Wait for the result of the send operation
            Response result = redisClient.send(Request.cmd(Command.EVAL)
                    .arg(singleRequestScript)
                    .arg(key)
                    .arg(String.valueOf(ttl))).toCompletionStage().toCompletableFuture().get();

            return result != null && result.toString().equals(SUCCESS);
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute RedisService script", e);
        }
    }

}
