package com.sm.bff.constant;

public interface RestConstant {
    interface PAGE {
        int PAGE_INDEX_DEFAULT = 0;
        int PAGE_SIZE_DEFAULT = 20;
        String PAGE_INDEX = "pageIndex";
        String PAGE_SIZE = "pageSize";
    }
    interface ACTION {
        String ACTION = "action";
    }

    interface NOTIFY {
        boolean READ = true;
        boolean UNREAD = false;
    }

    interface AUTH {

    }

    interface TOKEN {
        String TOKEN_PREFIX = "COM.SM.BFF.TOKEN.";
        String REFRESH_TOKEN_PREFIX = "COM.SM.BFF.REFRESH-TOKEN.";
        String USER_TOKEN_PREFIX = "COM.SM.BFF.USER-TOKEN.";
        /**
         * 3 tiếng
         */
        long TOKEN_TIME = 10800;
        /**
         * 3 ngày
         */
        long REFRESH_TOKEN_TIME = 259200;
    }
}
