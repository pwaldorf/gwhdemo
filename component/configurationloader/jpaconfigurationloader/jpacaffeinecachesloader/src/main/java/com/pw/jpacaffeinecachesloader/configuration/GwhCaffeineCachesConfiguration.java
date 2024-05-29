package com.pw.jpacaffeinecachesloader.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.gwhcore.configurations.GwhCoreProperties;
import com.pw.gwhcore.gwhcaffeinecache.GwhConfigurationLoader;
import com.pw.gwhcore.model.GwhCaffeineCache;
import com.pw.jpacaffeinecachesloader.jpa.service.GwhCaffeineCacheService;

@Component
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jpa.enabled", havingValue = "true", matchIfMissing = false)
public class GwhCaffeineCachesConfiguration implements GwhConfigurationLoader<GwhCaffeineCache> {

    private final GwhCoreProperties gwhCoreProperties;
    private final GwhCaffeineCacheService gwhCaffeineCacheService;

    public GwhCaffeineCachesConfiguration(GwhCoreProperties gwhCoreProperties, GwhCaffeineCacheService gwhCaffeineCacheService) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.gwhCaffeineCacheService = gwhCaffeineCacheService;
    }

    @Override
    public List<GwhCaffeineCache> getByProfile() {

        List<GwhCaffeineCache> gwhCaffeineCaches = (gwhCaffeineCacheService.getByProfile(gwhCoreProperties.getProfile()))
             .stream()
             .map(item -> new GwhCaffeineCache(item.getCacheName(), item.getCacheInitialCapacity(), item.getCacheMaximumCapacity()
                                                , item.getCacheEvictionType(), item.getCacheExpireAfterAccessTime(), item.getCacheExpireAfterWriteTime()
                                                , item.getCacheStatsEnabled(), item.getCacheStatsName(), item.getCacheLoaderName()))
             .collect(Collectors.toList());

        return gwhCaffeineCaches;

    }

    @Override
    public List<GwhCaffeineCache> getAll() {

        List<GwhCaffeineCache> gwhCaffeineCaches = (gwhCaffeineCacheService.getAll())
             .stream()
             .map(item -> new GwhCaffeineCache(item.getCacheName(), item.getCacheInitialCapacity(), item.getCacheMaximumCapacity()
                                                , item.getCacheEvictionType(), item.getCacheExpireAfterAccessTime(), item.getCacheExpireAfterWriteTime()
                                                , item.getCacheStatsEnabled(), item.getCacheStatsName(), item.getCacheLoaderName()))
             .collect(Collectors.toList());

        return gwhCaffeineCaches;

    }

    @Override
    public List<GwhCaffeineCache> getByProfileAndRegion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTemplatesByProfileAndRegion'");
    }

}
