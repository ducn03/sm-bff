package com.sm.bff.ws.service;

import com.sm.bff.ws.message.NotificationMessage;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class NotificationService {
    @Inject
    private WebSocketService webSocketService;

    public Uni<Void> sendNotification(String recipientId, String title, String content, String type) {
        NotificationMessage notification = new NotificationMessage();
        notification.setId(UUID.randomUUID().toString());
        notification.setRecipientId(recipientId);
        notification.setTitle(title);
        notification.setContent(content);
        notification.setNotificationType(type);
        notification.setTimestamp(Instant.now());

        return webSocketService.sendToUser(recipientId, notification);
    }

    public Uni<Void> broadcastNotification(String title, String content, String type) {
        NotificationMessage notification = new NotificationMessage();
        notification.setId(UUID.randomUUID().toString());
        notification.setTitle(title);
        notification.setContent(content);
        notification.setNotificationType(type);
        notification.setTimestamp(Instant.now());

        return webSocketService.broadcast(notification);
    }
}
