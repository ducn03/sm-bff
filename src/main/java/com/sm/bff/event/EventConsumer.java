package com.sm.bff.event;

import com.sm.bff.event.constant.EventTypes;
import com.sm.lib.helper.JsonHelper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.CustomLog;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
@CustomLog
public class EventConsumer {
    private final EventFactory eventFactory;

    public EventConsumer(EventFactory eventFactory) {
        this.eventFactory = eventFactory;
    }

    @Incoming(EventTypes.EVENT_KAFKA)
    public Uni<Void> handleEvent(String message) {
        EventMessage eventMessage = JsonHelper.toObject(message, EventMessage.class);
        if (eventMessage == null){
            log.info("Gửi message bị lỗi, vui lòng coi lại message gửi đi");
            log.info("Message: " + eventMessage);
            return Uni.createFrom().voidItem();
        }
        // Lấy service để handle
        BaseEventConsumer consumer = eventFactory.getConsumer(eventMessage.getType());
        if (consumer == null){
            log.info("⚠ No consumer found for event: " + eventMessage.getType());
            return Uni.createFrom().voidItem();
        }
        log.info("📩 Dispatching event: " + eventMessage.getType());
        return consumer.handleEvent(eventMessage.getData()).replaceWithVoid().invoke(() -> {
            log.info("SEND MESSAGE SUCCESS!!!");
        });
    }
}
