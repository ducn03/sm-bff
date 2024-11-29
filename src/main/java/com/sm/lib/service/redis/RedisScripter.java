package com.sm.lib.service.redis;

import com.sm.lib.config.RedisConfig;
import com.sm.lib.helper.UniHelper;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class RedisScripter {
    private final Redis redis;
    private final RedisCommander redisCommander;
    private final String singleRequestScript;
    private final String SUCCESS = "1";
    private final int KEY_QUANTITY = 2;

    @Inject
    public RedisScripter(Redis redis, RedisCommander redisCommander, RedisConfig redisConfig) {
        this.redis = redis;
        this.redisCommander = redisCommander;
        this.singleRequestScript = redisConfig.getSingleRequestScript();
    }

    public Uni<Boolean> singleRequest(String key, long ttl) {
        try {
            return UniHelper.toBooleanUni(redisCommander.executeEvalCommand(
                    singleRequestScript,
                    KEY_QUANTITY,
                    JsonArray.of(key, String.valueOf(ttl)))
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute RedisService script", e);
        }
    }

    public boolean singleRequestSync(String key, long ttl) {
        try {
            Response result = redisCommander.executeEvalCommand(singleRequestScript, KEY_QUANTITY,
                            JsonArray.of(key, String.valueOf(ttl)))
                            .toCompletableFuture()
                            .get();

            return result != null && SUCCESS.equals(result.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute RedisService script", e);
        }
    }


}
