package com.sm.bff.service.auth;

import com.sm.lib.dto.UserInfo;
import io.smallrye.mutiny.Uni;

public interface TokenService {
    /**
     * Lấy ra token
     */
    Uni<UserInfo> get(String token);
    /**
     * Lưu user info với token
     */
    void set(String token, UserInfo userInfo);

    /**
     * Xóa đi token
     */
    void delete(String token);

}
