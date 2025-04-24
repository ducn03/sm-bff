package com.sm.bff.config;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import lombok.CustomLog;

@ApplicationScoped
@CustomLog
public class RoutingContextConfig {
    /**
     * Khi sử dụng RoutingContext <br>
     * Phải cấu hình để có thể nhận được payload
     * @param router
     */
    public void setupRouter(@Observes Router router) {
        // Cấu hình BodyHandler cho toàn bộ route, hỗ trợ multipart/form-data
        BodyHandler bodyHandler = BodyHandler.create()
                // 50MB
                .setBodyLimit(50 * 1024 * 1024)
                // Cho phép xử lý file
                .setHandleFileUploads(true)
                // Lưu file vào thư mục "uploads"
                .setUploadsDirectory("uploads");

        router.route().handler(bodyHandler);

        // Log request path
        router.route().handler(routingContext -> {
            String path = routingContext.request().path();
            log.info("Path: " + path);

            if (!path.equals("/favicon.ico") && !path.equals("/")) {
                BodyHandler.create().handle(routingContext);
            } else {
                routingContext.next();
            }
        });
    }
}
