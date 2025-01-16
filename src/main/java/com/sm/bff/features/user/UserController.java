package com.sm.bff.features.user;

import com.sm.bff.service.UserDataService;
import com.sm.jpa.domain.User;
import com.sm.lib.exception.ErrorCodes;
import com.sm.lib.service.controller.ControllerService;
import com.sm.lib.helper.HttpHelper;
import com.sm.lib.utils.AppThrower;
import io.quarkus.hibernate.reactive.panache.common.WithTransaction;
import io.smallrye.mutiny.Uni;
import io.vertx.ext.web.RoutingContext;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/")
@Singleton
@WithTransaction
public class UserController {
    private final ControllerService controllerService;
    private final UserDataService userDataService;

    public UserController(ControllerService controllerService, UserDataService userDataService) {
        this.controllerService = controllerService;
        this.userDataService = userDataService;
    }

    @GET
    @Path("users")
    public Uni<Response> getAll() {
        var result = userDataService.getAll();
        return controllerService.success(result);
    }

    @GET
    @Path("user")
    public Uni<Response> get(@QueryParam("userId") long userId){
        var result = userDataService.getAll();
        return controllerService.success(result);
    }

    @POST
    @Path("user")
    public Uni<Response> create(RoutingContext request){
        User user = HttpHelper.body(request, User.class);
        if (user == null) AppThrower.ep(ErrorCodes.SYSTEM.BAD_REQUEST);
        var result = userDataService.create(user);
        return controllerService.success(result);
    }

    @PUT
    @Path("/user")
    public Uni<Response> update(RoutingContext request){
        User user = HttpHelper.body(request, User.class);
        if (user == null) AppThrower.ep(ErrorCodes.SYSTEM.BAD_REQUEST);
        var result = userDataService.update(user.getId(), user);
        return controllerService.success(result);
    }

    @DELETE
    @Path("user")
    public Uni<Response> delete(RoutingContext request){
        User user = HttpHelper.body(request, User.class);
        if (user == null) AppThrower.ep(ErrorCodes.SYSTEM.BAD_REQUEST);
        var result = userDataService.delete(user.getId());
        return controllerService.success(result);
    }
}
