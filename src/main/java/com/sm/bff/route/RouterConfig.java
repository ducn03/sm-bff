package com.sm.bff.route;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

/**
 * Khi sử dụng RoutingContext phải cấu hình để bật BodyHandler mới nhận được payload
 */
@ApplicationScoped
public class RouterConfig {
    public void setupRouter(@Observes Router router) {
        // Bật BodyHandler cho toàn bộ route
        router.route().handler(BodyHandler.create());
    }
}
