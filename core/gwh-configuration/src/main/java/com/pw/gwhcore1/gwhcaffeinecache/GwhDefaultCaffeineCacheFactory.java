package com.pw.gwhcore1.gwhcaffeinecache;

import com.pw.api1.configuration.GwhCaffeineCache;
import com.pw.api1.configuration.GwhCaffeineCacheFactory;
import org.springframework.stereotype.Component;

@Component
public class GwhDefaultCaffeineCacheFactory implements GwhCaffeineCacheFactory {
    public GwhCaffeineCache createCaffeineCache(String name,
                                                       Integer initialCapacity,
                                                       Integer maximumSize,
                                                       String evictionType,
                                                       Integer expireAfterAccessTime,
                                                       Integer expireAfterWriteTime,
                                                       Boolean statsEnabled,
                                                       String statsCounter,
                                                       String cacheLoader) {
        return new GwhDefaultCaffeineCache(name,
                initialCapacity,
                maximumSize,
                evictionType,
                expireAfterAccessTime,
                expireAfterWriteTime,
                statsEnabled,
                statsCounter,
                cacheLoader);
    }
}
