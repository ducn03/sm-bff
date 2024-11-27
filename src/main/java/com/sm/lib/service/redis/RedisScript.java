package com.sm.lib.service.redis;

import com.sm.lib.config.RedisConfig;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class RedisScript {
    private final Redis redis;
    private final String singleRequestScript;
    private final String SUCCESS = "1";
    private final int KEY_QUANTITY = 2;

    @Inject
    public RedisScript(Redis redis, RedisConfig redisConfig) {
        this.redis = redis;
        this.singleRequestScript = redisConfig.getSingleRequestScript();
    }

    public boolean singleRequest(String key, long ttl) {
        try {
            // Use EVALSHA instead of EVAL
            Response result = redis.send(Request.cmd(Command.EVAL)
                            .arg(singleRequestScript)
                            .arg(KEY_QUANTITY)
                            .arg(JsonArray.of(key, String.valueOf(ttl))))
                    .toCompletionStage().toCompletableFuture().get();


            return result != null && SUCCESS.equals(result.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute RedisService script", e);
        }
    }

}
