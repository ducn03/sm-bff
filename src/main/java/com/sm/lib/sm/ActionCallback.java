package com.sm.lib.sm;

public interface ActionCallback<T extends SMData> {

    void doBack(T data);
}
