package com.pw.gwhcore.gwhcaffeinecache;


import org.apache.camel.CamelContext;
import org.apache.camel.component.caffeine.CaffeineConfiguration;
import org.apache.camel.component.caffeine.EvictionType;
import org.apache.camel.component.caffeine.load.CaffeineLoadCacheComponent;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.stats.ConcurrentStatsCounter;
import com.github.benmanes.caffeine.cache.stats.StatsCounter;
import com.pw.gwhcore.model.GwhCaffeineCache;

import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GwhCaffeineCacheLoader implements GwhCacheLoader {

    CamelContext camelContext;

    ApplicationContext context;

    GwhConfigurationLoader<GwhCaffeineCache> gwhCaffeineCaches;

    public GwhCaffeineCacheLoader(CamelContext camelContext, ApplicationContext context, GwhConfigurationLoader<GwhCaffeineCache> gwhCaffeineCaches) {
        this.camelContext = camelContext;
        this.context = context;
        this.gwhCaffeineCaches = gwhCaffeineCaches;
    }

    @Override
    public void load() {

        Map<String, CaffeineConfiguration> configs = cacheConfigs();

        configs.entrySet().stream().forEach(config -> {
            camelContext.getRegistry().bind(config.getKey(), new CaffeineLoadCacheComponent() {
                {
                    setConfiguration(config.getValue());
                }
            });
        });
    }

    public Map<String, CaffeineConfiguration> cacheConfigs() {
    return gwhCaffeineCaches.getAll().stream()
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
