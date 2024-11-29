package com.sm.lib.helper;

import io.smallrye.mutiny.Uni;
import io.vertx.redis.client.Response;

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
                    List<Response> list = response.stream().toList();

                    for (int i = 0; i < list.size(); i += 2) {
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
                    return resultMap;
                });
    }



}
