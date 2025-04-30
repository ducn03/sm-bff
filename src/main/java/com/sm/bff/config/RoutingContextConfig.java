package com.sm.bff.config;

import io.vertx.ext.web.Router;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import lombok.CustomLog;

@ApplicationScoped
@ApplicationPath("/app")
@CustomLog
public class RoutingContextConfig extends Application {
    /**
     * Khi sử dụng RoutingContext <br>
     * Phải cấu hình để có thể nhận được payload
     * @param router
     */
    public void setupRouter(@Observes Router router) {
        router.route().handler(BodyHandlerConfig.createBodyHandler());
        // router.route().handler(CorsConfig.createCorsHandler());

        // Middleware log
        router.route().handler(routingContext -> {
            String path = routingContext.request().path();
            log.info("Path: " + path);
            routingContext.next();
        });
    }
}
