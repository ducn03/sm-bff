package com.sm.bff.ws.message;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class NotificationMessage implements WebSocketMessage{
    private String id;
    private String title;
    private String content;
    private String type;
    private String recipientId;
    private String notificationType;
    private Instant timestamp;
    @Override
    public String getType() {
        return "NOTIFICATION";
    }
}
