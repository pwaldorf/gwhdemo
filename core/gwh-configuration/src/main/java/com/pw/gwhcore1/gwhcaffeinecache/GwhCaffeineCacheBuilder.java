package com.pw.gwhcore1.gwhcaffeinecache;

import com.pw.api1.GwhBuilder;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import org.apache.camel.CamelContext;
import org.apache.camel.component.caffeine.CaffeineConfiguration;
import org.apache.camel.component.caffeine.EvictionType;
import org.apache.camel.component.caffeine.load.CaffeineLoadCacheComponent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.stats.ConcurrentStatsCounter;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import com.pw.api1.configuration.GwhCaffeineCache;

import java.util.Map;
import java.util.stream.Collectors;

public class GwhCaffeineCacheBuilder implements GwhBuilder {

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final CamelContext camelContext;
    private final ApplicationContext context;
    private final GwhDefaultResourceLoader<GwhCaffeineCache> gwhCaffeineCaches;

    public GwhCaffeineCacheBuilder(GwhConfigurationProperties gwhConfigurationProperties, CamelContext camelContext, ApplicationContext context,
                                   GwhDefaultResourceLoader<GwhCaffeineCache> gwhCaffeineCaches) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.camelContext = camelContext;
        this.context = context;
        this.gwhCaffeineCaches = gwhCaffeineCaches;
    }

    @Override
    public void build() {

        Map<String, CaffeineConfiguration> configs = cacheConfigs();

        configs.entrySet().forEach(config -> {
            camelContext.getRegistry().bind(config.getKey(), new CaffeineLoadCacheComponent() {
                {
                    setConfiguration(config.getValue());
                }
            });
        });
    }

    public Map<String, CaffeineConfiguration> cacheConfigs() {
    return gwhCaffeineCaches.getResource(gwhConfigurationProperties).stream()
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
                        configuration.setStatsCounter(context.getBean(item.getStatsCounter(), StatsCounter.class));
                    }
                }
                if (StringUtils.isNotEmpty(item.getCacheLoader())) {
                    configuration.setCacheLoader(context.getBean(item.getCacheLoader(), CacheLoader.class));
                }
                return configuration;
            }
        ));
    }

}