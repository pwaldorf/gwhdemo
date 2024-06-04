package com.pw.gwhcore.gwhroutes;

import com.pw.support.configuration.GwhCoreProperties;
import com.pw.support.configuration.GwhResource;
import com.pw.support.configuration.GwhResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.gwhcore.model.GwhRoute;

@Component
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteLoader extends GwhResourceLoader<GwhRoute> {
    public GwhRouteLoader(GwhCoreProperties gwhCoreProperties, GwhResource<GwhRoute> gwhResource) {
        super(gwhCoreProperties, gwhResource);
    }
}
