package com.sm.bff.route;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;

/**
 * Khi sử dụng RoutingContext phải cấu hình để bật BodyHandler mới nhận được payload
 */
@ApplicationPath("/app")
@ApplicationScoped
public class RouterConfig extends Application {
    public void setupRouter(@Observes Router router) {
        // Bật BodyHandler cho toàn bộ route
        router.route().handler(BodyHandler.create());
    }
}
