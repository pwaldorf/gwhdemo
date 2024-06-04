package com.pw.gwhcore.gwhcaffeinecache;

import com.pw.gwhcore.model.GwhCaffeineCache;
import com.pw.support.configuration.GwhCoreProperties;
import com.pw.support.configuration.GwhResource;
import com.pw.support.configuration.GwhResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhCaffeineCacheLoader extends GwhResourceLoader<GwhCaffeineCache> {
    public GwhCaffeineCacheLoader(GwhCoreProperties gwhCoreProperties, GwhResource<GwhCaffeineCache> gwhResource) {
        super(gwhCoreProperties, gwhResource);
    }
}
