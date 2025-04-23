package com.sm.bff.ws.service;

import com.sm.bff.ws.core.ConnectionContext;
import com.sm.bff.ws.core.WebSocketManager;
import com.sm.bff.ws.message.MessageSerializer;
import com.sm.bff.ws.message.WebSocketMessage;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class WebSocketService {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    @Inject
    private MessageSerializer messageSerializer;

    public Uni<Void> sendToUser(String userId, WebSocketMessage message) {
        ConnectionContext context = WebSocketManager.getInstance().getSessionRegistry().getContext(userId);
        if (context != null && context.getSession().isOpen()) {
            return sendToSession(context.getSession(), message);
        }
        return Uni.createFrom().nullItem();
    }

    public Uni<Void> sendToUsers(Set<String> userIds, WebSocketMessage message) {
        return Uni.join().all(
                userIds.stream()
                        .map(userId -> sendToUser(userId, message))
                        .collect(Collectors.toList())
        ).andFailFast().onItem().ignore().andContinueWithNull();
    }

    public Uni<Void> broadcast(WebSocketMessage message) {
        Set<Session> sessions = WebSocketManager.getInstance().getSessionRegistry().getAllSessions();
        return Uni.join().all(
                sessions.stream()
                        .map(session -> sendToSession(session, message))
                        .collect(Collectors.toList())
        ).andFailFast().onItem().ignore().andContinueWithNull();
    }

    private Uni<Void> sendToSession(Session session, WebSocketMessage message) {
        return Uni.createFrom().emitter(emitter -> {
            try {
                String serialized = messageSerializer.serialize(message);

                // Sử dụng CompletableFuture để xử lý bất đồng bộ
                session.getAsyncRemote().sendText(serialized, result -> {
                    if (result.isOK()) {
                        emitter.complete(null);
                    } else {
                        emitter.fail(result.getException());
                    }
                });
            } catch (Exception e) {
                logger.error("Failed to send message to session", e);
                emitter.fail(e);
            }
        });
    }
}
