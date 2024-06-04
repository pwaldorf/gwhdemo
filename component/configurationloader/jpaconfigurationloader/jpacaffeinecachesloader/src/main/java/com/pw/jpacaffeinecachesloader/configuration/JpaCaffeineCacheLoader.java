package com.pw.jpacaffeinecachesloader.configuration;

import com.pw.gwhcore.configurations.GwhCoreProperties;
import com.pw.gwhcore.gwhcaffeinecache.GwhCaffeineCacheResource;
import com.pw.gwhcore.model.GwhCaffeineCache;
import com.pw.jpacaffeinecachesloader.jpa.service.GwhCaffeineCacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jpa.enabled", havingValue = "true", matchIfMissing = false)
public class JpaCaffeineCacheLoader implements GwhCaffeineCacheResource {

    private final GwhCaffeineCacheService gwhCaffeineCacheService;

    public JpaCaffeineCacheLoader(GwhCaffeineCacheService gwhCaffeineCacheService) {
        this.gwhCaffeineCacheService = gwhCaffeineCacheService;
    }

    @Override
    public List<GwhCaffeineCache> getResourceAll() {
        return (gwhCaffeineCacheService.getAll())
                .stream()
                .map(item -> new GwhCaffeineCache(item.getCacheName(), item.getCacheInitialCapacity(), item.getCacheMaximumCapacity()
                        , item.getCacheEvictionType(), item.getCacheExpireAfterAccessTime(), item.getCacheExpireAfterWriteTime()
                        , item.getCacheStatsEnabled(), item.getCacheStatsName(), item.getCacheLoaderName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhCaffeineCache> getResourceByProfile(String profile) {
        return List.of();
    }

    @Override
    public List<GwhCaffeineCache> getResourceByProfileAndRegion(String profile, String region) {
        return List.of();
    }
}
