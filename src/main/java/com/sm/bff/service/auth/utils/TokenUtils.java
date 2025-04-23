package com.sm.bff.service.auth.utils;

import java.security.SecureRandom;

public class TokenUtils {
    private static final char[] CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String generate(int length) {
        char[] token = new char[length];
        for (int i = 0; i < length; i++) {
            token[i] = CHAR_POOL[SECURE_RANDOM.nextInt(CHAR_POOL.length)];
        }
        return new String(token);
    }
}
