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
}
