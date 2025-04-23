package com.sm.bff.ws.endpoint;

import com.sm.bff.ws.core.WebSocketManager;
import com.sm.bff.ws.handler.HandlerFactory;
import com.sm.bff.ws.handler.MessageHandler;
import com.sm.bff.ws.message.MessageSerializer;
import com.sm.bff.ws.message.WebSocketMessage;
import jakarta.inject.Inject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(value = "/ws", configurator = EndpointConfigurator.class)
public class WebSocketEndpoint {
    private static final Logger logger = LoggerFactory.getLogger(WebSocketEndpoint.class);

    @Inject
    private HandlerFactory handlerFactory;

    @Inject
    private MessageSerializer messageSerializer;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {
        String userId = (String) config.getUserProperties().get("userId");
        if (userId == null || userId.isEmpty()) {
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.VIOLATED_POLICY, "Authentication required"));
                return;
            } catch (IOException e) {
                logger.error("Error closing unauthenticated session", e);
            }
        }

        Map<String, Object> attributes = new HashMap<>();
        WebSocketManager.getInstance().getSessionRegistry().register(userId, session, attributes);
        logger.info("WebSocket connection opened for user: {}", userId);
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        try {
            WebSocketMessage wsMessage = messageSerializer.deserialize(message);
            MessageHandler handler = handlerFactory.getHandler(wsMessage.getType());
            handler.handle(session, wsMessage);
        } catch (Exception e) {
            logger.error("Error processing message", e);
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        String userId = getUserIdFromSession(session);
        if (userId != null) {
            WebSocketManager.getInstance().getSessionRegistry().unregister(userId);
            logger.info("WebSocket connection closed for user: {}, reason: {}", userId, reason.getReasonPhrase());
        }
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        String userId = getUserIdFromSession(session);
        logger.error("WebSocket error for user: {}", userId, throwable);
    }

    private String getUserIdFromSession(Session session) {
        return (String) session.getUserProperties().get("userId");
    }
}
