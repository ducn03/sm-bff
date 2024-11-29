package com.sm.lib.service.redis;

import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.redis.client.Command;
import io.vertx.redis.client.Redis;
import io.vertx.redis.client.Request;
import io.vertx.redis.client.Response;
import io.vertx.core.json.JsonArray;
import jakarta.inject.Singleton;

import java.util.concurrent.CompletionStage;

@Singleton
public class RedisCommander {
    private final Redis redis;

    public RedisCommander(Redis redis) {
        this.redis = redis;
    }

    public CompletionStage<Response> executeCommand(Redis redis, Request request) {
        return redis.send(request).toCompletionStage();
    }

    public CompletionStage<Response> executeCommand(Request request) {
        return this.redis.send(request).toCompletionStage();
    }

    /**
     * Dùng để tạo request cho Command.SET <br>
     * Input: key, value, ttl nếu cần
     */
    public Request set(String key, String value){
        return buildRequest(Command.SET, key, value);
    }

    public Request set(String key, String value, String EX, String ttl){
        return buildRequest(Command.SET, key, value, EX, ttl);
    }

    /**
     * Dùng để tạo request cho Command.HSET <br>
     * Input: key, hashKey, value, ttl nếu cần
     */
    public Request hSet(String key, String hashKey, String value){
        return buildRequest(Command.HSET, key, hashKey, value);
    }

    /**
     * Tạo Command.SET request và execute <br>
     * Input: key, value
     */
    public CompletionStage<Response> executeSetCommand(String key, String value){
        return executeCommand(set(key, value));
    }

    /**
     * Tạo Command.SET request và execute <br>
     * Input: key, value, EX, ttl
     */
    public CompletionStage<Response> executeSetCommand(String key, String value, String EX, String ttl){
        return executeCommand(set(key, value, EX, ttl));
    }

    /**
     * Tạo Command.HSET request và execute <br>
     * Input: key, hashKey, value
     */
    public CompletionStage<Response> executeHSetCommand(String key, String hashKey, String value){
        return executeCommand(hSet(key, hashKey, value));
    }

    /**
     * Dùng để tạo request cho Command.GET <br>
     * Input: key
     */
    public Request get(String key){
        return buildRequest(Command.GET, key);
    }

    /**
     * Dùng để tạo request cho Command.HGETALL <br>
     * Input: key
     */
    public Request hGetAll(String key){
        return buildRequest(Command.HGETALL, key);
    }

    /**
     * Dùng để tạo request cho Command.HGET
     * Input: key, hashKey
     */
    public Request hGet(String key, String hashKey){
        return buildRequest(Command.HGET, key, hashKey);
    }

    /**
     * Tạo Command.GET request và execute <br>
     * Input: key
     */
    public CompletionStage<Response> executeGetCommand(String key){
        return executeCommand(get(key));
    }

    /**
     * Tạo Command.HGETALL request và execute <br>
     * Input: key
     */
    public CompletionStage<Response> executeHGetAllCommand(String key){
        return executeCommand(hGetAll(key));
    }

    /**
     * Tạo Command.HGET request và execute <br>
     * Input: key, hashkey
     */
    public CompletionStage<Response> executeHGetCommand(String key, String hashKey){
        return executeCommand(hGet(key, hashKey));
    }

    /**
     * Dùng để tạo request cho Command.DEL <br>
     * Input: key
     */
    public Request delete(String key){
        return buildRequest(Command.DEL, key);
    }

    /**
     * Dùng để tạo request cho Command.HDEL <br>
     * Input: key, hashKey
     */
    public Request hDelete(String key, String hashKey){
        return buildRequest(Command.HDEL, key, hashKey);
    }

    /**
     * Tạo Command.DEL request và execute <br>
     * Input: key
     */
    public CompletionStage<Response> executeDelCommand(String key){
        return executeCommand(delete(key));
    }

    /**
     * Dùng để tạo request cho Command.HDEL và execute <br>
     * Input: key, hashKey
     */
    public CompletionStage<Response> executeHDelCommand(String key, String hashKey){
        return executeCommand(hDelete(key, hashKey));
    }

    /**
     * Tạo lệnh EVAL
     */
    public Request eval(Object... args) {
        return buildRequest(Command.EVAL, args);
    }


    /**
     * Tạo và thực thi lệnh EVAL
     */
    public CompletionStage<Response> executeEvalCommand(Object... args) {
        return executeCommand(eval(args));
    }


    /**
     * Dùng để build request
     */
    public Request buildRequest(Command command, Object... args) {
        Request request = Request.cmd(command);
        for (Object arg : args) {
            if (arg instanceof String){
                request.arg(arg.toString());
                continue;
            }
            if (arg instanceof Integer) {
                request.arg((int) arg);
                continue;
            }
            if (arg instanceof Boolean) {
                request.arg((boolean) arg);
                continue;
            }
            if (arg instanceof JsonArray) {
                request.arg((JsonArray) arg);
                continue;
            }
            if (arg instanceof Long || arg instanceof Short || arg instanceof Byte) {
                request.arg(((Number) arg).longValue());
                continue;
            }
            if (arg instanceof JsonObject) {
                request.arg((JsonObject) arg);
                continue;
            }
            if (arg instanceof Buffer) {
                request.arg((Buffer) arg);
                continue;
            }
            if (arg instanceof Float || arg instanceof Double) {
                request.arg(arg.toString());
                continue;
            }
        }
        return request;
    }

}
