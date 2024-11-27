package com.sm.bff.features.user;

import com.sm.bff.service.UserDataService;
import com.sm.jpa.domain.User;
import com.sm.lib.service.controller.ControllerService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/")
@Singleton
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
        System.out.println(userId);
        var result = userDataService.getAll();
        return controllerService.success(result);
    }

    @POST
    @Path("user")
    public Uni<Response> create(){
        return controllerService.success(userDataService.create(new User()));
    }

//    @POST
//    @Path("/user")
//    public Uni<User> update(){
//        return userDataService.create(new User());
//    }
//
//    @POST
//    @Path("user")
//    public Uni<User> delete(){
//        return userDataService.create(new User());
//    }
}
