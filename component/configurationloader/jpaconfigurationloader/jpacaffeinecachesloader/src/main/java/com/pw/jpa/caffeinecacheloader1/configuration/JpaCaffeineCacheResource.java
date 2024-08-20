package com.pw.jpa.caffeinecacheloader1.configuration;

import com.pw.api1.GwhProfileResource;
import com.pw.api1.configuration.GwhCaffeineCache;
import com.pw.api1.configuration.GwhCaffeineCacheFactory;
import com.pw.jpa.caffeinecacheloader1.jpa.service.GwhCaffeineCacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.caches.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class JpaCaffeineCacheResource implements GwhProfileResource<GwhCaffeineCache> {

    private final GwhCaffeineCacheService gwhCaffeineCacheService;
    private final GwhCaffeineCacheFactory gwhCaffeineCacheFactory;

    public JpaCaffeineCacheResource(GwhCaffeineCacheService gwhCaffeineCacheService, GwhCaffeineCacheFactory gwhCaffeineCacheFactory) {
        this.gwhCaffeineCacheService = gwhCaffeineCacheService;
        this.gwhCaffeineCacheFactory = gwhCaffeineCacheFactory;
    }

    @Override
    public List<GwhCaffeineCache> getResourceAll() {
        return (gwhCaffeineCacheService.getAll())
                .stream()
                .map(item -> gwhCaffeineCacheFactory.createCaffeineCache(item.getCacheName(), item.getCacheInitialCapacity(), item.getCacheMaximumCapacity()
                        , item.getCacheEvictionType(), item.getCacheExpireAfterAccessTime(), item.getCacheExpireAfterWriteTime()
                        , item.getCacheStatsEnabled(), item.getCacheStatsName(), item.getCacheLoaderName()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhCaffeineCache> getResourceByProfile(String profile) {
        return List.of();
    }

    @Override
    public List<GwhCaffeineCache> getResourceByProfileAndRegionAndVersion(String profile, String region, String version) {
        return List.of();
    }
}
