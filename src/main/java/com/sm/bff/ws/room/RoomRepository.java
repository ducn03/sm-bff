package com.sm.bff.ws.room;

import com.sm.lib.service.redis.Redis;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;
import java.util.Objects;
import java.util.Set;

@ApplicationScoped
public class RoomRepository {
    private static final String ROOM_PREFIX = "websocket:room:";

    @Inject
    Redis redis;

    public void addMemberToRoom(String roomId, String userId) {
        redis.hSet(ROOM_PREFIX + roomId, userId, "1");
    }

    public void removeMemberFromRoom(String roomId, String userId) {
        redis.hDelete(ROOM_PREFIX + roomId, userId);
    }

    public Uni<Set<String>> getRoomMembers(String roomId) {
        return redis
                .hGetAll(ROOM_PREFIX + roomId)
                .map(Map::keySet);
    }


    public void deleteRoom(String roomId) {
        redis.delete(ROOM_PREFIX + roomId);
    }

    public Uni<Boolean> isMemberInRoom(String roomId, String userId) {
        return redis.hGet(ROOM_PREFIX + roomId, userId).map(Objects::nonNull);
    }
}