package com.sm.bff.ws.handler;

import com.sm.bff.ws.message.ChatMessage;
import com.sm.bff.ws.message.WebSocketMessage;
import com.sm.bff.ws.service.ChatService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.websocket.Session;

public class ChatMessageHandler implements MessageHandler {
    @Inject
    private ChatService chatService;

    @Override
    public Uni<Void> handle(Session session, WebSocketMessage message) {
        if (message instanceof ChatMessage chatMessage) {
            return chatService.processMessage(session, chatMessage)
                    .onItem().ignore().andContinueWithNull();
        }
        return Uni.createFrom().nullItem();
    }

    @Override
    public String getType() {
        return "CHAT";
    }
}
