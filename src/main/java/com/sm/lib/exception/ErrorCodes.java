package com.sm.lib.exception;

public interface ErrorCodes {
    int OK = 200;
    public interface SYSTEM {
        int SYSTEM_ERROR = 500;
        int BAD_REQUEST = 400;
        int BAD_GATEWAY = 502;
        int UNAUTHORIZED = 401;

        int PAGE_NOT_FOUND = 404;

        interface SM {

            int BAD_REQUEST_INPUT_NOT_FOUND = 40001001;
            int BAD_REQUEST_STATE_NOT_FOUND = 40001002;
            int BAD_REQUEST_ACTION_NOT_FOUND = 40001003;
        }
    }

    interface LOGIN {
        int PHONE_NOT_FOUND = 40012001;
        int WRONG_PASSWORD = 40012002;
        int WRONG_USERNAME = 40012003;
    }
}
