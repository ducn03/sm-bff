package com.sm.lib.service.redis;

import com.sm.lib.helper.UniHelper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;

@ApplicationScoped
public class RedisService {
    /**
     * Expire time in second
     */
    private final String EX = "EX";
    private final RedisScripter redisScripter;
    private final RedisCommander redisCommander;

    @Inject
    public RedisService(RedisScripter redisScripter, RedisCommander redisCommander) {
        this.redisScripter = redisScripter;
        this.redisCommander = redisCommander;
    }

    public void set(String key, String value) {
        redisCommander.executeSetCommand(key, value);
    }

    public void set(String key, String value, long ttl) {
        redisCommander.executeSetCommand(key, value, EX, String.valueOf(ttl));
    }

    public void hSet(String key, String hashKey, String value){
        redisCommander.executeHSetCommand(key, hashKey, value);
    }

    public Uni<String> get(String key) {
        return UniHelper.toStringUni(redisCommander.executeGetCommand(key));
    }

    public Uni<Map<String, Object>> hGetAll(String key){
        return UniHelper.toMapUni(redisCommander.executeHGetAllCommand(key));
    }

    public Uni<String> hGet(String key, String hashKey){
        return UniHelper.toStringUni(redisCommander.executeHGetCommand(key, hashKey));
    }

    public void delete(String key) {
        redisCommander.executeDelCommand(key);
    }

    public void hDelete(String key, String hashKey){
        redisCommander.executeHDelCommand(key, hashKey);
    }

    public Uni<Boolean> singleRequest(String key, long ttl) {
        return redisScripter.singleRequest(key, ttl);
    }

}
