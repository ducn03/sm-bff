package com.sm.bff.service.auth;

import com.sm.lib.dto.UserToken;
import io.smallrye.mutiny.Uni;

public interface UserTokenService {
    /**
     * Lấy đối tượng UserTokenService từ Redis
     */
    Uni<UserToken> getUserToken(String username);
    /**
     * Thêm token vào danh sách token của user và trả về UserInfo
     */
    void addTokenForUser(UserToken userToken, String username, String newToken);
    /**
     * Xóa token khỏi danh sách token của user
     */
    void removeTokenForUser(UserToken userToken, String oldToken);
    /**
     * Lưu đối tượng UserTokenService vào Redis với thời gian lưu trữ = thời gian lưu trữ REFRESH TOKEN mới nhất
     */
    void saveUserTokenInRedis(String username, UserToken userToken);

    Uni<Void> removeAllToken(String username);
}
