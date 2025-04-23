package com.sm.bff.ws.message;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class ChatMessage implements WebSocketMessage {
    private String id;
    private String senderId;
    private String roomId;
    private String content;
    private Instant timestamp;

    @Override
    public String getType() {
        return "CHAT";
    }

    // Getters, setters, constructors
}
