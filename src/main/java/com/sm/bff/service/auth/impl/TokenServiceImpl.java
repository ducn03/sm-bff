package com.sm.bff.service.auth.impl;

import com.sm.bff.constant.RestConstant;
import com.sm.bff.service.auth.TokenService;
import com.sm.lib.dto.UserInfo;
import com.sm.lib.helper.JsonHelper;
import com.sm.lib.service.redis.Redis;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenServiceImpl implements TokenService {

    private final Redis redis;
    private final String TOKEN_PREFIX = RestConstant.TOKEN.TOKEN_PREFIX;
    private final long TOKEN_TIME = RestConstant.TOKEN.TOKEN_TIME;
    public TokenServiceImpl(Redis redis) {
        this.redis = redis;
    }

    @Override
    public Uni<UserInfo> get(String token) {
        return redis.get(TOKEN_PREFIX + token).map(userInfoStr -> {
            return JsonHelper.toObject(userInfoStr, UserInfo.class);
        });
    }


    @Override
    public void set(String token, UserInfo userInfo) {
        // 3 hours
        redis.set(TOKEN_PREFIX + token, JsonHelper.toJson(userInfo), TOKEN_TIME);
    }

    @Override
    public void delete(String token) {
        redis.delete(TOKEN_PREFIX + token);
    }
}
