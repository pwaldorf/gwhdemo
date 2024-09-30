package com.pw.gwhcore1.gwhcaffeinecache;

import com.pw.api1.GwhBuilder;
import com.pw.api1.GwhProfileResource;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.support1.util.ApplicationContextProvider;

import lombok.extern.slf4j.Slf4j;

import org.apache.camel.CamelContext;
import org.apache.camel.component.caffeine.CaffeineConfiguration;
import org.apache.camel.component.caffeine.EvictionType;
import org.apache.camel.component.caffeine.load.CaffeineLoadCacheComponent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.stats.ConcurrentStatsCounter;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import com.pw.api1.configuration.GwhCaffeineCache;

import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.load.caches.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhCaffeineCacheBuilder implements GwhBuilder {

    private GwhProfileResource<GwhCaffeineCache> gwhResource;

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final CamelContext camelContext;

    public GwhCaffeineCacheBuilder(GwhConfigurationProperties gwhConfigurationProperties, CamelContext camelContext) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.camelContext = camelContext;
    }

    @Override
    public void build() {

        Map<String, CaffeineConfiguration> configs = cacheConfigs();

        log.info("Building Caffeine Caches: {}", configs.keySet());
        configs.entrySet().forEach(config -> {
            camelContext.getRegistry().bind(config.getKey(), new CaffeineLoadCacheComponent() {
                {
                    setConfiguration(config.getValue());
                }
            });
        });
    }

    public Map<String, CaffeineConfiguration> cacheConfigs() {
    return gwhResource.getResource(gwhConfigurationProperties.getProfile(), gwhConfigurationProperties.getRegion(), gwhConfigurationProperties.getVersion())
        .stream()
        .collect(Collectors.toMap(
            GwhCaffeineCache::getName,
            item -> {
                CaffeineConfiguration configuration = new CaffeineConfiguration();
                configuration.setStatsEnabled(item.getStatsEnabled());
                configuration.setEvictionType(EvictionType.getEvictionType(item.getEvictionType()));
                configuration.setInitialCapacity(item.getInitialCapacity());
                configuration.setMaximumSize(item.getMaximumSize());
                configuration.setExpireAfterWriteTime(item.getExpireAfterWriteTime());
                configuration.setExpireAfterAccessTime(item.getExpireAfterAccessTime());
                if (StringUtils.isNotEmpty(item.getStatsCounter())) {
                    if (item.getStatsCounter().equals("default")) {
                        configuration.setStatsCounter(new ConcurrentStatsCounter());
                    } else {
                        configuration.setStatsCounter(ApplicationContextProvider.applicationContext.getBean(
                            item.getStatsCounter(), StatsCounter.class));
                    }
                }
                if (StringUtils.isNotEmpty(item.getCacheLoader())) {
                    configuration.setCacheLoader(ApplicationContextProvider.applicationContext.getBean(
                        item.getCacheLoader(), CacheLoader.class));
                }
                return configuration;
            }
        ));
    }

    @Autowired
    public void setGwhResource(GwhProfileResource<GwhCaffeineCache> gwhResource) {
        this.gwhResource = gwhResource;
    }

}