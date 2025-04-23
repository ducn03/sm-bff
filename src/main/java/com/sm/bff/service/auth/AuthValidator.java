package com.sm.bff.service.auth;

import jakarta.inject.Singleton;
import org.mindrot.jbcrypt.BCrypt;

@Singleton
public class AuthValidator {
    private final int COST_FACTOR = 12;
    public boolean isEqualPassword(String inputPassword, String storedHash) {
        return BCrypt.checkpw(inputPassword, storedHash);
    }

    /**
     * Băm mật khẩu
     * @param plainPassword
     * @return
     */
    public String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt(COST_FACTOR));
    }

}
