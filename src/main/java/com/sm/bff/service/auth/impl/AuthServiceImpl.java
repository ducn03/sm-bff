package com.sm.bff.service.auth.impl;

import com.sm.bff.service.auth.*;
import com.sm.bff.service.auth.request.LoginRequest;
import com.sm.jpa.domain.core.Status;
import com.sm.jpa.repository.UserRepository;
import com.sm.lib.dto.UserInfo;
import com.sm.lib.dto.UserToken;
import com.sm.lib.exception.ErrorCodes;
import com.sm.lib.utils.AppThrower;
import com.sm.lib.utils.StringUtils;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final RefreshTokenService refreshTokenService;
    private final UserTokenService userTokenService;
    private final AuthValidator authValidator;
    private final AuthDataBuilder authDataBuilder;

    public AuthServiceImpl(UserRepository userRepository, TokenService tokenService, RefreshTokenService refreshTokenService, UserTokenService userTokenService, AuthValidator authValidator, AuthDataBuilder authDataBuilder) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.refreshTokenService = refreshTokenService;
        this.userTokenService = userTokenService;
        this.authValidator = authValidator;
        this.authDataBuilder = authDataBuilder;
    }

    /**
     * Đăng nhập
     * @param request
     * @return
     */
    @Override
    public Uni<UserInfo> login(LoginRequest request){
        String username = request.getUsername();
        String password = request.getPassword();
        if (StringUtils.isNullOrEmpty(username)){
            AppThrower.ep(ErrorCodes.LOGIN.WRONG_USERNAME);
        }
        return loginWithUsername(username, password);
    }

    private Uni<UserInfo> loginWithUsername(String username, String password) {
        return userRepository.findByUsername(username)
                .onItem().ifNull().failWith(() -> {
                    AppThrower.ep(ErrorCodes.LOGIN.WRONG_USERNAME);
                    return null;
                })
                .flatMap(user -> {
                    if (!authValidator.isEqualPassword(password, user.getPassword())) {
                        AppThrower.ep(ErrorCodes.LOGIN.WRONG_PASSWORD);
                    }
                    if (user.getStatus() != Status.ACTIVE.getValue()){
                        AppThrower.ep(ErrorCodes.SYSTEM.FORBIDDEN);
                    }
                    return authDataBuilder.toUserInfo(user);
                })
                // Lưu Token lên redis
                .invoke(userInfo -> cacheTokenIfAbsent(userInfo.getToken(), userInfo.getRefreshToken(), userInfo))
                // Cập nhật token mới vào danh sách token của người dùng
                .flatMap(userInfo -> {
                    // Step 1: Lấy danh sách
                    return userTokenService.getUserToken(username).map(userToken -> {
                        // check user token == null thì khởi tạo đối tượng mới
                        if (userToken == null)  userToken = new UserToken();
                        // Step 2: Thêm token vào danh sách
                        userTokenService.addTokenForUser(userToken, username, userInfo.getToken());
                        // Step 3: Lưu vào redis
                        userTokenService.saveUserTokenInRedis(username, userToken);
                        return userInfo;
                    });
                });
    }

    private void cacheTokenIfAbsent(String token, String refreshToken, UserInfo userInfo) {
        tokenService.set(token, userInfo);
        refreshTokenService.set(refreshToken, userInfo);
    }


    /**
     * Làm mới token, hoặc cấp lại token nếu hết hạn
     */
    @Override
    public Uni<UserInfo> refreshToken(String refreshToken) {
        return refreshTokenService.get(refreshToken)
                .flatMap(oldUserInfo -> {
                    if (oldUserInfo == null) AppThrower.ep(ErrorCodes.SYSTEM.BAD_REQUEST);
                    String username = oldUserInfo.getUsername();
                    return userRepository.findByUsername(username)
                            .onItem().ifNull().failWith(() -> {
                                AppThrower.ep(ErrorCodes.SYSTEM.UNAUTHORIZED);
                                return null;
                            })
                            // Xóa bỏ token cũ và đăng nhập lại lấy token và refresh token mới
                            .flatMap(dbUser -> {
                                refreshTokenService.delete(refreshToken);
                                tokenService.delete(oldUserInfo.getToken());
                                return loginWithUsername(dbUser.getUsername(), dbUser.getPassword());
                            })
                            // Lấy danh sách token của người dùng
                            .call(userInfo -> userTokenService.getUserToken(username).map(userToken -> {
                                // Lúc call loginWithUsername, sẽ tự add 1 cái token
                                // userTokenService.addTokenForUser(userToken, username, userInfo.getToken());
                                // Bỏ token cũ
                                userTokenService.removeTokenForUser(userToken, oldUserInfo.getToken());
                                // Lưu Redis
                                userTokenService.saveUserTokenInRedis(username, userToken);
                                return userInfo;
                            }));
                });
    }

    /**
     * Đăng xuất tài khoản ra khỏi thiết bị
     * @param token
     * @return
     */
    @Override
    public Uni<Boolean> logout(String token) {
        return tokenService.get(token).flatMap(userInfo -> {
                    if (userInfo == null) AppThrower.ep(ErrorCodes.SYSTEM.BAD_REQUEST);
                    String refreshToken = userInfo.getRefreshToken();
                    // Xóa token và refresh token
                    tokenService.delete(token);
                    refreshTokenService.delete(refreshToken);
                    // Loại bỏ token ra khỏi danh token của người dùng
                    String username = userInfo.getUsername();
                    return userTokenService.getUserToken(username).map(userToken -> {
                        userTokenService.removeTokenForUser(userToken, token);
                        userTokenService.saveUserTokenInRedis(username, userToken);
                        return true;
                    });
                })
                .onFailure().recoverWithUni(() -> Uni.createFrom().item(false));
    }

    /**
     * Đăng xuất khỏi toàn bộ thiết bị
     * @param username
     * @return
     */
    @Override
    public Uni<Boolean> logoutAllDevice(String username) {
        return userTokenService.getUserToken(username)
                .flatMap(userToken -> {
                    if (userToken == null || userToken.getTokenList().isEmpty()) {
                        return Uni.createFrom().item(true);
                    }
                    return Multi.createFrom().iterable(userToken.getTokenList())
                            // Dùng transformToUniAndMerge để chuyển mỗi token thành một Uni từ logout(token)
                            .onItem().transformToUniAndMerge(this::logout)
                            .collect().asList()
                            .onItem().transform(list -> true);
                });
    }


}
