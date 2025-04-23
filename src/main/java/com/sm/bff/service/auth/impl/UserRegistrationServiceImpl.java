package com.sm.bff.service.auth.impl;

import com.sm.bff.service.auth.UserRegistrationService;
import com.sm.bff.service.auth.request.UserRegisterRequest;
import io.smallrye.mutiny.Uni;

public class UserRegistrationServiceImpl implements UserRegistrationService {
    @Override
    public Uni<Boolean> register(UserRegisterRequest request) {
        return null;
    }
}
