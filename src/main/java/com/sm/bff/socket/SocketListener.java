package com.sm.bff.socket;

import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;

import java.io.IOException;

public interface SocketListener {
    void onOpen(Session session);

    void onMessage(Session session, String message) throws IOException;

    void onClose(Session session, CloseReason closeReason);

    void onError(Session session, Throwable t);
}
