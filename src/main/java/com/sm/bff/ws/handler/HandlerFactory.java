package com.sm.bff.ws.handler;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class HandlerFactory {
    private final Map<String, MessageHandler> handlers = new HashMap<>();

    @Inject
    public HandlerFactory(Instance<MessageHandler> handlerInstances) {
        for (MessageHandler handler : handlerInstances) {
            handlers.put(handler.getType(), handler);
        }
    }

    public MessageHandler getHandler(String type) {
        MessageHandler handler = handlers.get(type);
        if (handler == null) {
            throw new IllegalArgumentException("No handler found for message type: " + type);
        }
        return handler;
    }
}
