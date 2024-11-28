package com.sm.lib.utils;

import com.sm.lib.exception.AppException;

public class AppThrower {

    public static void ep(int errorCode) {
        throw new AppException(errorCode);
    }
}
