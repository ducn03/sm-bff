package com.sm.bff.route;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import lombok.CustomLog;

/**
 * Khi sử dụng RoutingContext phải cấu hình để bật BodyHandler mới nhận được payload
 */
@ApplicationPath("/app")
@ApplicationScoped
@CustomLog
public class RouterConfig extends Application {
    public void setupRouter(@Observes Router router) {
        router.route().handler(routingContext -> {
            String path = routingContext.request().path();
            log.debug(path);
            if (!path.equals("/favicon.ico") && !path.equals("/")) {
                BodyHandler.create().handle(routingContext);
            } else {
                routingContext.next();
            }
        });
    }
}
