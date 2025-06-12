package com.sm.bff.service.auth.impl;

import com.sm.bff.constant.RestConstant;
import com.sm.bff.service.auth.TokenService;
import com.sm.bff.service.auth.UserTokenService;
import com.sm.lib.dto.UserToken;
import com.sm.lib.exception.ErrorCodes;
import com.sm.lib.helper.JsonHelper;
import com.sm.lib.service.redis.Redis;
import com.sm.lib.utils.AppThrower;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.CustomLog;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@CustomLog
@ApplicationScoped
public class UserTokenServiceImpl implements UserTokenService {

    private final Redis redis;
    private final TokenService tokenService;
    private final String USER_TOKEN_PREFIX = RestConstant.TOKEN.USER_TOKEN_PREFIX;
    private final long REFRESH_TOKEN_TIME = RestConstant.TOKEN.REFRESH_TOKEN_TIME;

    public UserTokenServiceImpl(Redis redis, TokenService tokenService) {
        this.redis = redis;
        this.tokenService = tokenService;
    }

    @Override
    public Uni<UserToken> getUserToken(String username) {
        return redis.get(USER_TOKEN_PREFIX + username)
                .map(userTokenStr -> JsonHelper.toObject(userTokenStr, UserToken.class));
    }

    @Override
    public void addTokenForUser(UserToken userToken, String username, String newToken) {
        userToken.setUsername(username);
        if (userToken.getTokenList() == null ||
                userToken.getTokenList().isEmpty())
        {
            userToken.setTokenList(new HashSet<>());
        }
        userToken.getTokenList().add(newToken);
        log.info("User token: " + userToken);
    }

    @Override
    public void removeTokenForUser(UserToken userToken, String oldToken) {
        if (userToken == null) {
            AppThrower.ep(ErrorCodes.SYSTEM.UNAUTHORIZED);
        }
        log.info("Token list: " + userToken.getTokenList());
        userToken.getTokenList().remove(oldToken);
        log.info("Token list after remove: " + userToken.getTokenList());
    }

    @Override
    public void saveUserTokenInRedis(String username, UserToken userToken) {
        if (userToken.getTokenList().isEmpty()){
            redis.delete(USER_TOKEN_PREFIX + username);
        } else {
            redis.set(USER_TOKEN_PREFIX + username, JsonHelper.toJson(userToken), REFRESH_TOKEN_TIME);
        }
    }

    @Override
    public Uni<Void> removeAllToken(String username){
        return getUserToken(username).flatMap(userToken -> {
            if (userToken == null || userToken.getTokenList().isEmpty()){
                return Uni.createFrom().voidItem();
            }
            List<String> removedToken = new ArrayList<>();
            for (String token : userToken.getTokenList()){
                tokenService.delete(token);
                removedToken.add(token);
            }
            for (String token : removedToken){
                removeTokenForUser(userToken, token);
            }
            saveUserTokenInRedis(username, userToken);
            return Uni.createFrom().voidItem();
        });
    }

}
