package com.sm.lib.service.redis;

import com.sm.lib.helper.UniHelper;
import io.smallrye.mutiny.Uni;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.client.Response;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Singleton
public class RedisScripter {
    private final RedisCommander redisCommander;
    private final String SINGLE_REQUEST_SCRIPT_PATH = "src/main/resources/script/redis/single-request.lua";
    private final String SUCCESS = "1";
    private final int KEY_QUANTITY = 2;

    @Inject
    public RedisScripter(RedisCommander redisCommander) {
        this.redisCommander = redisCommander;
    }

    public Uni<Boolean> singleRequest(String key, long ttl) {
        try {
            return UniHelper.toBooleanUni(redisCommander.executeEvalCommand(
                    loadScripts(SINGLE_REQUEST_SCRIPT_PATH),
                    KEY_QUANTITY,
                    JsonArray.of(key, String.valueOf(ttl)))
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute Redis script", e);
        }
    }

    public boolean singleRequestSync(String key, long ttl) {
        try {
            Response result = redisCommander.executeEvalCommand(loadScripts(SINGLE_REQUEST_SCRIPT_PATH), KEY_QUANTITY,
                            JsonArray.of(key, String.valueOf(ttl)))
                            .toCompletableFuture()
                            .get();

            return result != null && SUCCESS.equals(result.toString());
        } catch (Exception e) {
            throw new RuntimeException("Failed to execute Redis script", e);
        }
    }

    private String loadScripts(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Failed to load Redis script", e);
        }
    }

}
