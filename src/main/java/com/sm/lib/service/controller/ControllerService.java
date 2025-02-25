package com.sm.lib.service.controller;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.core.Response;

public interface ControllerService {
    // Chấp nhận cả Uni và Object
    Uni<Response> success(Object data);
    Uni<Response> success(Object data, Object pagination);

    Uni<Response> error(int error, String message);

    Uni<Response> systemError();
}
