package com.sm.lib.utils;

public interface ILazyCache<T extends CacheData> {

    public T get();
    public T load();
}
