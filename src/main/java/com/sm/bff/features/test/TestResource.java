package com.sm.bff.features.test;

import com.sm.bff.service.TestSingleRequestService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("test")
@Singleton
public class TestResource {
    private final TestSingleRequestService testSingleRequestService;

    @Inject
    public TestResource(TestSingleRequestService testSingleRequestService) {
        this.testSingleRequestService = testSingleRequestService;
    }

    @GET
    public boolean test(){
        return testSingleRequestService.isRequested("key", 60);
    }
}
