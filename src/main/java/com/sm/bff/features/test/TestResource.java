package com.sm.bff.features.test;

import com.sm.bff.service.TestSingleRequestService;
import com.sm.lib.service.controller.ControllerService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("test")
@Singleton
public class TestResource {
    private final TestSingleRequestService testSingleRequestService;
    private final ControllerService controllerService;

    @Inject
    public TestResource(TestSingleRequestService testSingleRequestService, ControllerService controllerService) {
        this.testSingleRequestService = testSingleRequestService;
        this.controllerService = controllerService;
    }

    @GET
    public Uni<Response> test(){
        return controllerService.success(testSingleRequestService.isRequested("key", 60));
    }
}
