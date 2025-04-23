package com.sm.bff.service.auth.impl;

import com.sm.bff.constant.RestConstant;
import com.sm.bff.service.auth.RefreshTokenService;
import com.sm.lib.dto.UserInfo;
import com.sm.lib.exception.ErrorCodes;
import com.sm.lib.helper.JsonHelper;
import com.sm.lib.service.redis.Redis;
import com.sm.lib.utils.AppThrower;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class RefreshTokenServiceImpl implements RefreshTokenService {
    private final Redis redis;
    private final String REFRESH_TOKEN_PREFIX = RestConstant.TOKEN.REFRESH_TOKEN_PREFIX;
    private final long REFRESH_TOKEN_TIME = RestConstant.TOKEN.REFRESH_TOKEN_TIME;


    public RefreshTokenServiceImpl(Redis redis) {
        this.redis = redis;
    }

    @Override
    public Uni<UserInfo> get(String refreshToken) {
        return redis.get(REFRESH_TOKEN_PREFIX + refreshToken)
                .onItem().ifNull().failWith(() -> {
                    AppThrower.ep(ErrorCodes.SYSTEM.UNAUTHORIZED);
                    return null;
                })
                .onItem()
                .transform(userInfoStr -> JsonHelper.toObject(userInfoStr, UserInfo.class));
    }

    @Override
    public void set(String refreshToken, UserInfo userInfo) {
        // 3 days
        redis.set(REFRESH_TOKEN_PREFIX + refreshToken, JsonHelper.toJson(userInfo), REFRESH_TOKEN_TIME);
    }

    @Override
    public void delete(String refreshToken) {
        redis.delete(REFRESH_TOKEN_PREFIX + refreshToken);
    }
}
