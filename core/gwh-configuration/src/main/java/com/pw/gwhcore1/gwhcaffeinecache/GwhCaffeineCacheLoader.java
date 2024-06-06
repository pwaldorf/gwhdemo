package com.pw.gwhcore1.gwhcaffeinecache;

import com.pw.gwhcore1.model.GwhCaffeineCache;
import com.pw.support1.configuration.GwhCoreProperties;
import com.pw.support1.configuration.GwhResource;
import com.pw.support1.configuration.GwhResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.caches.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhCaffeineCacheLoader extends GwhResourceLoader<GwhCaffeineCache> {
    public GwhCaffeineCacheLoader(GwhCoreProperties gwhCoreProperties, GwhResource<GwhCaffeineCache> gwhResource) {
        super(gwhCoreProperties, gwhResource);
    }
}
