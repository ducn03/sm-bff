package com.sm.bff.features.auth;

import com.sm.bff.service.auth.AuthService;
import com.sm.bff.service.auth.request.LoginRequest;
import com.sm.lib.helper.HttpHelper;
import com.sm.lib.service.controller.ControllerService;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@ApplicationScoped
@WithTransaction
@Path("/auth")
public class AuthController {
    private final ControllerService controllerService;
    private final AuthService authService;

    public AuthController(ControllerService controllerService, AuthService authService) {
        this.controllerService = controllerService;
        this.authService = authService;
    }

    @POST
    @Path("/login")
    public Uni<Response> login(RoutingContext context) {
        LoginRequest payload = HttpHelper.body(context, LoginRequest.class);
        if (payload == null) controllerService.success(null);
        assert payload != null;
        return controllerService.success(authService.login(payload));
    }

    @POST
    @Path("/logout")
    public Uni<Response> logout(RoutingContext context) {
        String token = HttpHelper.getHeaderAuthorization(context);
        return controllerService.success(authService.logout(token));
    }

    @POST
    @Path("/refresh-token")
    public Uni<Response> refreshToken(RoutingContext context) {
        String refreshToken = HttpHelper.getHeaderAuthorization(context);
        return controllerService.success(authService.refreshToken(refreshToken));
    }
}
