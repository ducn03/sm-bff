package com.sm.bff.service.auth;

import com.sm.bff.service.auth.utils.TokenUtils;
import com.sm.jpa.domain.User;
import com.sm.lib.dto.UserInfo;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthDataBuilder {

    public Uni<UserInfo> toUserInfo(User user) {
        UserInfo userInfo = new UserInfo();

        userInfo.setUsername(user.getUsername());
        userInfo.setFullName(user.getFullName());
        userInfo.setPhone(user.getPhone());
        userInfo.setToken(TokenUtils.generate(12));
        userInfo.setRefreshToken(TokenUtils.generate(24));

        return Uni.createFrom().item(userInfo);
    }
}
