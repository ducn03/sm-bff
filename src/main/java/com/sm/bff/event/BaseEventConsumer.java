package com.sm.bff.event;

import io.smallrye.mutiny.Uni;

public interface BaseEventConsumer {
    Uni<Void> handleEvent(String message);
}
