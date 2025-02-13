package com.sm.bff.socket;

import com.sm.lib.service.redis.Redis;
import com.sm.lib.utils.ILazyCache;
import com.sm.lib.utils.LazyCache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.websocket.CloseReason;
import jakarta.websocket.Session;
import lombok.CustomLog;

import java.io.IOException;
import java.net.SocketException;
import java.util.HashMap;

@ApplicationScoped
@CustomLog
public class SocketServer {
    private static final String SOCKET_ROOM = "COM.SM.BFF.SOCKET.ROOM.";
    private final ILazyCache<LazyAnalytics> analyticsCache;

    private final HashMap<String, Session> sessionHashMap = new HashMap<>();
    private final Object lock = new Object();
    private final Redis redis;

    public SocketServer(ILazyCache<LazyAnalytics> analyticsCache, Redis redis) {
        this.redis = redis;
        this.analyticsCache = analyticsCache;

        SocketManager.getInstance().setListener(initSocketListener());
    }


    private SocketListener initSocketListener() {
        return new SocketListener() {
            @Override
            public void onOpen(Session session) {
                analyticsCache.get();
                String signature = session.getQueryString();
                if (signature == null || signature.isEmpty()) {
                    try {
                        session.close();
                    } catch (IOException ignored) {
                    }
                    return;
                }
                sessionHashMap.put(signature, session);
                log.info(String.format("WebSocket opened: %s", session.getId()));
            }

            @Override
            public void onMessage(Session session, String message) throws IOException {
                try {
                    log.info(String.format("Message received: %s", message));
                    session.getBasicRemote().sendText(message.toUpperCase());
                } catch (IOException e) {
                    log.error("Error sending message to session " + session.getId(), e);
                    session.close();
                }
            }

            @Override
            public void onError(Session session, Throwable t) {
                log.error(String.format("Error in WebSocket session %s%n", session == null ? "null" : session.getId()), t);
                if (t instanceof SocketException) {
                    log.info("Connection reset detected in WebSocket session " + session.getId());
                    sessionHashMap.remove(session.getQueryString()); // Xóa session nếu có lỗi kết nối
                }
            }

            @Override
            public void onClose(Session session, CloseReason reason) {
                analyticsCache.get();
                CloseReason.CloseCode closeCode = reason != null ? reason.getCloseCode() : CloseReason.CloseCodes.NORMAL_CLOSURE;
                String reasonPhrase = (reason != null && reason.getReasonPhrase() != null) ? reason.getReasonPhrase() : "unknown reason";
                log.info(String.format("Closing a WebSocket (%s) due to %s (code: %s)", session.getId(), reasonPhrase, closeCode));
                sessionHashMap.remove(session.getQueryString());
            }

        };
    };


}
