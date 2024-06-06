package com.pw.gwhcore1.gwhroutes;

import com.pw.support1.configuration.GwhCoreProperties;
import com.pw.support1.configuration.GwhResource;
import com.pw.support1.configuration.GwhResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.gwhcore1.model.GwhRoute;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.routes.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteLoader extends GwhResourceLoader<GwhRoute> {
    public GwhRouteLoader(GwhCoreProperties gwhCoreProperties, GwhResource<GwhRoute> gwhResource) {
        super(gwhCoreProperties, gwhResource);
    }
}
