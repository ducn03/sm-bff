package com.sm.lib.config;

import io.quarkus.redis.datasource.RedisCommands;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.impl.RedisClient;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@ApplicationScoped
public class RedisConfig {
    private final Redis redisClient;
    private String singleRequestScript;

    public RedisConfig(Redis redisClient) {
        loadScripts();
        this.redisClient = redisClient;
    }

    private void loadScripts() {
        try {
            this.singleRequestScript = Files.readString(Path.of("src/main/resources/script/redis/single-request.lua"));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load RedisService script", e);
        }
    }

    public Redis getRedisClient() {
        return redisClient;
    }

    public String getSingleRequestScript() {
        return singleRequestScript;
    }

}
