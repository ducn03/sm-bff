package com.sm.bff.ws.handler;

import com.sm.bff.ws.message.WebSocketMessage;
import io.smallrye.mutiny.Uni;
import jakarta.websocket.Session;

public interface MessageHandler {
    Uni<Void> handle(Session session, WebSocketMessage message);
    String getType();
}
