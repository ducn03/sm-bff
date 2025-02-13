package com.sm.bff.socket;

import com.sm.lib.utils.ILazyCache;
import com.sm.lib.utils.LazyCache;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;

@ApplicationScoped
public class LazyCacheProducer {

    @Produces
    @ApplicationScoped
    public ILazyCache<LazyAnalytics> createLazyAnalyticsCache() {
        return new LazyCache<>(3000) {
            @Override
            public LazyAnalytics load() {
                return new LazyAnalytics();
            }
        };
    }
}
