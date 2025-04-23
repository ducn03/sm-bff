package com.sm.bff.service.auth;

import com.sm.bff.service.auth.request.UserRegisterRequest;
import io.smallrye.mutiny.Uni;

public interface UserRegistrationService {
    /**
     * Đăng ký tài khoản
     */
    public Uni<Boolean> register(UserRegisterRequest request);
}
