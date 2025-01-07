package com.sm.lib.service.redis;

import com.sm.lib.constant.RedisConstant;
import com.sm.lib.helper.UniHelper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;

@ApplicationScoped
public class Redis {
    /**
     * Expire time in second
     */
    private final String EX = RedisConstant.TIME.EX;
    private final RedisScripter redisScripter;
    private final RedisCommander redisCommander;

    @Inject
    public Redis(RedisScripter redisScripter, RedisCommander redisCommander) {
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

    public Uni<Long> increment(String key) {
        return UniHelper.toLongUni(redisCommander.executeIncrementCommand(key));
    }

    /**
     * FOR Z LEADERBOARD
     */
    public void zAdd(String key, double score, String member){
        redisCommander.executeZAdd(key, score, member);
    }

    public void zIncrement(String key, double delta, String member){
        redisCommander.executeZIncrement(key, delta, member);
    }

    public void ZDecrement(String key, double delta, String member){
        redisCommander.executeZDecrement(key, delta, member);
    }

    public Uni<Long> zRank(String key, String member){
        return UniHelper.toLongUni(redisCommander.executeZRank(key, member));
    }

    public Uni<Long> zRevRank(String key, String member){
        return UniHelper.toLongUni(redisCommander.executeZRevRank(key, member));
    }

    public Uni<Map<String, Double>> zRangeWithScores(String key, long start, long end){
        return UniHelper.toMapWithScoresUni(redisCommander.executeZRangeWithScores(key, start, end));
    }

    public Uni<Map<String, Double>> zRevRangeWithScores(String key, long start, long end){
        return UniHelper.toMapWithScoresUni(redisCommander.executeZRevRangeWithScores(key, start, end));
    }

    public void zRemove(String key, String member){
        redisCommander.executeZRemove(key, member);
    }

    public void setExpire(String key, long ttl){
        redisCommander.executeSetExpire(key, ttl);
    }

    public Uni<Boolean> singleRequest(String key, long ttl) {
        return redisScripter.singleRequest(key, ttl);
    }

}
