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
    public Request set(Object... args){
        return buildRequest(Command.SET, args);
    }

    /**
     * Dùng để tạo request cho Command.HSET <br>
     * Input: key, hashKey, value, ttl nếu cần
     */
    public Request hSet(Object... args){
        return buildRequest(Command.HSET, args);
    }

    /**
     * Tạo Command.SET request và execute <br>
     * Input: key, value, (EX, ttl) nếu cần
     */
    public CompletionStage<Response> executeSetCommand(Object... args){
        return executeCommand(set(args));
    }

    /**
     * Tạo Command.HSET request và execute <br>
     * Input: key, hashKey, value
     */
    public CompletionStage<Response> executeHSetCommand(Object... args){
        return executeCommand(hSet(args));
    }

    /**
     * Dùng để tạo request cho Command.GET <br>
     * Input: key
     */
    public Request get(Object... args){
        return buildRequest(Command.GET, args);
    }

    /**
     * Dùng để tạo request cho Command.HGETALL <br>
     * Input: key
     */
    public Request hGetAll(Object... args){
        return buildRequest(Command.HGETALL, args);
    }

    /**
     * Dùng để tạo request cho Command.HGET
     * Input: key, hashKey
     */
    public Request hGet(Object... args){
        return buildRequest(Command.HGET, args);
    }

    /**
     * Tạo Command.GET request và execute <br>
     * Input: key
     */
    public CompletionStage<Response> executeGetCommand(Object... args){
        return executeCommand(get(args));
    }

    /**
     * Tạo Command.HGETALL request và execute <br>
     * Input: key
     */
    public CompletionStage<Response> executeHGetAllCommand(Object... args){
        return executeCommand(hGetAll(args));
    }

    /**
     * Tạo Command.HGET request và execute <br>
     * Input: key, hashkey
     */
    public CompletionStage<Response> executeHGetCommand(Object... args){
        return executeCommand(hGet(args));
    }

    /**
     * Dùng để tạo request cho Command.DEL <br>
     * Input: key
     */
    public Request delete(Object... args){
        return buildRequest(Command.DEL, args);
    }

    /**
     * Dùng để tạo request cho Command.HDEL <br>
     * Input: key, hashKey
     */
    public Request hDelete(Object... args){
        return buildRequest(Command.HDEL, args);
    }

    /**
     * Tạo Command.DEL request và execute <br>
     * Input: key
     */
    public CompletionStage<Response> executeDelCommand(Object... args){
        return executeCommand(delete(args));
    }

    /**
     * Dùng để tạo request cho Command.HDEL và execute <br>
     * Input: key, hashKey
     */
    public CompletionStage<Response> executeHDelCommand(Object... args){
        return executeCommand(hDelete(args));
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
