package com.sm.bff.ws.handler;

import com.sm.bff.ws.message.NotificationMessage;
import com.sm.bff.ws.message.WebSocketMessage;
import com.sm.bff.ws.service.NotificationService;
import io.smallrye.mutiny.Uni;
import jakarta.websocket.Session;

public class NotificationHandler implements MessageHandler{
    private final NotificationService notificationService;

    public NotificationHandler(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @Override
    public Uni<Void> handle(Session session, WebSocketMessage message) {
        if (message instanceof NotificationMessage notificationMessage) {
//            return notificationService.sendNotification(notificationMessage.getRecipientId())
//                    .onItem().ignore().andContinueWithNull();
            return null;
        }
        return Uni.createFrom().nullItem();
    }

    @Override
    public String getType() {
        return null;
    }
}
