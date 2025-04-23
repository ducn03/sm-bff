package com.sm.bff.service.auth;

import com.sm.lib.dto.UserInfo;
import io.smallrye.mutiny.Uni;

public interface RefreshTokenService {
    /**
     * Lấy ra refresh token
     */
    Uni<UserInfo> get(String refreshToken);

    /**
     * Lưu UserInfo với refresh token
     */
    void set(String refreshToken, UserInfo userInfo);

    /**
     * Xóa đi refresh token
     */
    void delete(String refreshToken);
}
