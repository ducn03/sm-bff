package com.sm.lib.queue;

import io.vertx.core.eventbus.EventBus;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.CustomLog;

@ApplicationScoped
@CustomLog
public class EventBusProducer {
    private final EventBus eventBus;

    public EventBusProducer(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    public void sendMessage(String topic, String message) {
        // Gửi message đến tất cả subscriber
        log.info("Send message to: " + topic);
        log.info("message: " + message);
        eventBus.publish(topic, message);
    }
}
