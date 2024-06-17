package com.pw.gwhcore1.gwhcaffeinecache;

import com.pw.api1.configuration.GwhCaffeineCache;

public class GwhDefaultCaffeineCache implements GwhCaffeineCache {

    String name;
    Integer initialCapacity;
    Integer maximumSize;
    String evictionType;
    Integer expireAfterAccessTime;
    Integer expireAfterWriteTime;
    Boolean statsEnabled;
    String statsCounter;
    String cacheLoader;

    public GwhDefaultCaffeineCache(String name,
                                   Integer initialCapacity,
                                   Integer maximumSize,
                                   String evictionType,
                                   Integer expireAfterAccessTime,
                                   Integer expireAfterWriteTime,
                                   Boolean statsEnabled,
                                   String statsCounter,
                                   String cacheLoader) {
        this.name = name;
        this.initialCapacity = initialCapacity;
        this.maximumSize = maximumSize;
        this.evictionType = evictionType;
        this.expireAfterAccessTime = expireAfterAccessTime;
        this.expireAfterWriteTime = expireAfterWriteTime;
        this.statsEnabled = statsEnabled;
        this.statsCounter = statsCounter;
        this.cacheLoader = cacheLoader;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getInitialCapacity() {
        return initialCapacity;
    }

    public void setInitialCapacity(Integer initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }

    public String getEvictionType() {
        return evictionType;
    }

    public void setEvictionType(String evictionType) {
        this.evictionType = evictionType;
    }

    public Integer getExpireAfterAccessTime() {
        return expireAfterAccessTime;
    }

    public void setExpireAfterAccessTime(Integer expireAfterAccessTime) {
        this.expireAfterAccessTime = expireAfterAccessTime;
    }

    public Integer getExpireAfterWriteTime() {
        return expireAfterWriteTime;
    }

    public void setExpireAfterWriteTime(Integer expireAfterWriteTime) {
        this.expireAfterWriteTime = expireAfterWriteTime;
    }

    public Boolean getStatsEnabled() {
        return statsEnabled;
    }

    public void setStatsEnabled(Boolean statsEnabled) {
        this.statsEnabled = statsEnabled;
    }

    public String getStatsCounter() {
        return statsCounter;
    }

    public void setStatsCounter(String statsCounter) {
        this.statsCounter = statsCounter;
    }

    public String getCacheLoader() {
        return cacheLoader;
    }

    public void setCacheLoader(String cacheLoader) {
        this.cacheLoader = cacheLoader;
    }
}
