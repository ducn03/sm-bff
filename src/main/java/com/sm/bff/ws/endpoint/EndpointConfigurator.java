package com.sm.bff.ws.endpoint;

import com.sm.bff.ws.utils.SecurityUtils;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

public class EndpointConfigurator extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        String token = extractTokenFromRequest(request);
        String userId = SecurityUtils.validateToken(token);

        if (userId != null) {
            sec.getUserProperties().put("userId", userId);
        }
    }

    private String extractTokenFromRequest(HandshakeRequest request) {
        // Trích xuất token từ request - có thể từ query string hoặc headers
        return request.getParameterMap().getOrDefault("token", null).get(0);
    }
}
