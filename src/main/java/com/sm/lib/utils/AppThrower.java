package com.hc.lib.utils;

import com.hc.lib.exception.AppException;

public class AppThrower {

    public static void ep(int errorCode) {
        throw new AppException(errorCode);
    }
}
