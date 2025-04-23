package com.sm.bff.service.auth;

import com.sm.bff.service.auth.request.LoginRequest;
import com.sm.lib.dto.UserInfo;
import io.smallrye.mutiny.Uni;

public interface AuthService {
    /**
     * Đăng nhập
     */
    Uni<UserInfo> login(LoginRequest request);

    /**
     * Cấp lại token mới
     */
    Uni<UserInfo> refreshToken(String refreshToken);

    /**
     * Đăng xuất
     */
    Uni<Boolean> logout(String token);

    /**
     * Đăng xuất toàn thiết bị
     */
    Uni<Boolean> logoutAllDevice(String username);
}
