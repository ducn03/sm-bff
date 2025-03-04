package com.sm.bff.event;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Map;

@ApplicationScoped
public class EventFactory {
    private final Map<String, BaseEventConsumer> consumers;

    @Inject
    public EventFactory() {
        this.consumers = Map.of(
                // EventTypes.CHART.ADD, chartConfigConsumer
        );
    }

    public BaseEventConsumer getConsumer(String eventType) {
        return consumers.get(eventType);
    }
}
