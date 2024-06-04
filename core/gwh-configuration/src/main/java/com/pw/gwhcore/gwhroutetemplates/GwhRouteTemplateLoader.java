package com.pw.gwhcore.gwhroutetemplates;

import com.pw.gwhcore.model.GwhRouteTemplate;
import com.pw.support.configuration.GwhCoreProperties;
import com.pw.support.configuration.GwhResource;
import com.pw.support.configuration.GwhResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplateLoader extends GwhResourceLoader<GwhRouteTemplate> {

    public GwhRouteTemplateLoader(GwhCoreProperties gwhCoreProperties, GwhResource<GwhRouteTemplate> gwhResource) {
        super(gwhCoreProperties, gwhResource);
    }
}
