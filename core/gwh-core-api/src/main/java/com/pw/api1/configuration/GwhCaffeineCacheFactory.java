package com.pw.api1.configuration;

public interface GwhCaffeineCacheFactory {
    public GwhCaffeineCache createCaffeineCache(String name,
                                                       Integer initialCapacity,
                                                       Integer maximumSize,
                                                       String evictionType,
                                                       Integer expireAfterAccessTime,
                                                       Integer expireAfterWriteTime,
                                                       Boolean statsEnabled,
                                                       String statsCounter,
                                                       String cacheLoader);
}
