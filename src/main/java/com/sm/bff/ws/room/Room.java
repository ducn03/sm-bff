package com.sm.bff.ws.room;

import io.smallrye.mutiny.Uni;

import java.util.Set;

public interface Room {
    String getId();
    void addMember(String userId);
    void removeMember(String userId);
    Uni<Set<String>> getMembers();
    Uni<Boolean> hasMember(String userId);
}
