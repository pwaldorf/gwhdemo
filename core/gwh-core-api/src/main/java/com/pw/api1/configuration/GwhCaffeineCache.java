package com.pw.api1.configuration;

public interface GwhCaffeineCache {

    public String getName();

    public void setName(String name);

    public Integer getInitialCapacity();

    public void setInitialCapacity(Integer initialCapacity);

    public Integer getMaximumSize();

    public void setMaximumSize(Integer maximumSize);

    public String getEvictionType();

    public void setEvictionType(String evictionType);

    public Integer getExpireAfterAccessTime();

    public void setExpireAfterAccessTime(Integer expireAfterAccessTime);

    public Integer getExpireAfterWriteTime();

    public void setExpireAfterWriteTime(Integer expireAfterWriteTime);

    public Boolean getStatsEnabled();

    public void setStatsEnabled(Boolean statsEnabled);

    public String getStatsCounter();

    public void setStatsCounter(String statsCounter);

    public String getCacheLoader();

    public void setCacheLoader(String cacheLoader);

}
