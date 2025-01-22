package com.sm.bff.socket;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.CustomLog;

import java.io.IOException;


@ServerEndpoint(value = "/webSocket", encoders = MessageEncoder.class, decoders = MessageDecoder.class)
@ApplicationScoped
@CustomLog
public class SocketConnector {

    private SocketListener listener;

    @OnOpen
    public void onOpen(Session session) {
        this.listener = SocketManager.getInstance().getListener();
        this.listener.onOpen(session);
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        if (this.listener != null) {
            this.listener.onMessage(session, message);
        }
    }

    @OnClose
    public void onClose(CloseReason reason, Session session) {
        if (this.listener != null) {
            this.listener.onClose(session, reason);
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        if (this.listener != null) {
            this.listener.onError(session, t);
            // giữ listener không remove để các socket khác vẫn dùng đc
            // this.listener = null;
        }

    }

}