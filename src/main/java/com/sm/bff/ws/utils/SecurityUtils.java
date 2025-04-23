package com.sm.bff.ws.utils;

import jakarta.inject.Singleton;

@Singleton
public class SecurityUtils {

    public static String validateToken(String token) {
        // Phương thức đơn giản để demo - trong thực tế cần xác thực JWT
        if (token != null && !token.isEmpty()) {
            // Giả sử token có format user-123
            return token;
        }
        return null;
    }

//    public Uni<String> getUserIdFromToken(String token) {
//        return Uni.createFrom().emitter(emitter -> {
//            try {
//                JsonWebToken jwt = parser.parse(token);
//                String userId = jwt.getSubject();
//                emitter.complete(userId);
//            } catch (Exception e) {
//                emitter.fail(e);
//            }
//        });
//    }
}
