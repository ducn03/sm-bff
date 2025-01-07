package com.sm.lib.helper;

import io.smallrye.mutiny.Uni;
import io.vertx.redis.client.Response;
import io.vertx.redis.client.impl.types.BulkType;
import io.vertx.redis.client.impl.types.MultiType;

import java.util.*;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

public class UniHelper {

    private static final String SUCCESS = "1";

    public static Uni<Void> toVoidUni(CompletionStage<Response> stage) {
        return Uni.createFrom()
                .completionStage(stage)
                .replaceWithVoid();
    }

    public static Uni<String> toStringUni(CompletionStage<Response> stage) {
        return Uni.createFrom()
                .completionStage(stage)
                .map(response -> response != null ? response.toString() : null);
    }

    public static Uni<Boolean> toBooleanUni(CompletionStage<Response> stage) {
        return Uni.createFrom()
                .completionStage(stage)
                .map(response -> response != null && SUCCESS.equals(response.toString()));
    }

    public static Uni<Set<Object>> toSetObjectUni(CompletionStage<Response> stage) {
        return Uni.createFrom()
                .completionStage(stage)
                .map(response -> {
                    if (response == null || response.size() == 0) {
                        return Set.of(); // Trả về set rỗng nếu không phải mảng
                    }
                    return response.stream()
                            .map(resp -> resp != null ? resp.toString() : null)
                            .collect(Collectors.toCollection(LinkedHashSet::new)); // Chuyển đổi thành tập hợp
                });
    }

    public static Uni<Map<String, Object>> toMapUni(CompletionStage<Response> stage) {
        return Uni.createFrom()
                .completionStage(stage)
                .map(response -> {
                    if (response == null || response.size() == 0) {
                        return Map.of();
                    }
                    Map<String, Object> resultMap = new LinkedHashMap<>();
                    System.out.println(response);
                    List<Response> list = response.stream().toList();
                    System.out.println("log");
                    System.out.println(list);
                    System.out.println(list.size());
                    System.out.println(list.get(0));
                    System.out.println(list.get(0).getClass().getName());
                    if (list.get(0) instanceof BulkType){
                        System.out.println("Not list");
                        for (int i = 0; i < list.size() - 1; i ++) {
                            if (i % 2 == 1) continue;
                            String key = list.get(i).toString(); // Key
                            String value = list.get(i + 1).toString(); // Value

                            if (value.startsWith("{") && value.endsWith("}")) {
                                resultMap.put(key, JsonHelper.toObject(value, Map.class));
                                continue;
                            }
                            if (value.startsWith("[") && value.endsWith("]")) {
                                resultMap.put(key, JsonHelper.toObject(value, List.class));
                                continue;
                            }
                            resultMap.put(key, value);
                        }
                    }
                    if (response instanceof MultiType multiType){
                        System.out.println("key value");
                        Set<String> keys = multiType.getKeys();
                        for (String key : keys){
                            String value = multiType.get(key).toString();
                            System.out.println(key);
                            System.out.println(value);

                            if (value.startsWith("{") && value.endsWith("}")) {
                                resultMap.put(key, JsonHelper.toObject(value, Map.class));
                                continue;
                            }
                            if (value.startsWith("[") && value.endsWith("]")) {
                                resultMap.put(key, JsonHelper.toObject(value, List.class));
                                continue;
                            }
                            resultMap.put(key, value);
                        }
                    }
                    System.out.println(resultMap);
                    return resultMap;
                });
    }

    public static Uni<Long> toLongUni(CompletionStage<Response> stage) {
        return Uni.createFrom()
                .completionStage(stage)
                .map(response -> response != null ? response.toLong() : null);
    }

    public static Uni<Map<String, Double>> toMapWithScoresUni(CompletionStage<Response> stage) {
        return Uni.createFrom()
                .completionStage(stage)
                .map(response -> {
                    if (response == null || response.size() == 0) {
                        return Map.of();
                    }
                    Map<String, Double> result = new LinkedHashMap<>();
                    List<Response> list = response.stream().toList();
                    if (list.get(0) instanceof BulkType){
                        for (int i = 0; i < list.size(); i += 2) {
                            String member = list.get(i).toString();
                            Double score = Double.valueOf(list.get(i + 1).toString());
                            result.put(member, score);
                        }
                    }
                    if (list.get(0) instanceof MultiType){
                        for (Response value : list) {
                            String member = value.get(0).toString();
                            Double score = Double.valueOf(value.get(1).toString());
                            result.put(member, score);
                        }
                    }
                    return result;
                });
    }

}
