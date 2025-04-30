package com.sm.bff.config;

import io.vertx.core.http.HttpMethod;
import io.vertx.ext.web.handler.CorsHandler;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

@Provider
public class CorsConfig implements ContainerResponseFilter {
    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) {
        // Xử lý các yêu cầu CORS cho các phương thức GET, POST
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST");
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "*");
        responseContext.getHeaders().add("Access-Control-Max-Age", "3600");
    }

//    public static CorsHandler createCorsHandler() {
//        return CorsHandler.create()
//                .addOrigin("*")
//                .allowedMethod(HttpMethod.GET)
//                .allowedMethod(HttpMethod.POST)
//                .allowedHeader("*")
//                //.allowCredentials(true)
//                .maxAgeSeconds(3600);
//    }
}
