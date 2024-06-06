package com.pw.jpa.caffeinecacheloader1.configuration;

import com.pw.gwhcore1.gwhcaffeinecache.GwhCaffeineCacheResource;
import com.pw.gwhcore1.model.GwhCaffeineCache;
import com.pw.jpa.caffeinecacheloader1.jpa.service.GwhCaffeineCacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.caches.jpa1.enabled", havingValue = "true", matchIfMissing = false)
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
