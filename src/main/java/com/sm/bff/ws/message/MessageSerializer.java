package com.sm.bff.ws.message;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class MessageSerializer {
    @Inject
    private ObjectMapper objectMapper;

    private final Map<String, Class<? extends WebSocketMessage>> typeMap = new HashMap<>();

    public MessageSerializer() {
        // Đăng ký các loại tin nhắn
        typeMap.put("CHAT", ChatMessage.class);
        typeMap.put("NOTIFICATION", NotificationMessage.class);
    }

    public String serialize(WebSocketMessage message) throws IOException {
        return objectMapper.writeValueAsString(message);
    }

    public WebSocketMessage deserialize(String json) throws IOException {
        Map<String, Object> messageMap = objectMapper.readValue(json, Map.class);
        String type = (String) messageMap.get("type");

        Class<? extends WebSocketMessage> messageClass = typeMap.get(type);
        if (messageClass == null) {
            throw new IllegalArgumentException("Unknown message type: " + type);
        }

        return objectMapper.readValue(json, messageClass);
    }
}
