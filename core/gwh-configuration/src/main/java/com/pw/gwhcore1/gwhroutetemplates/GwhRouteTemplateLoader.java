package com.pw.gwhcore1.gwhroutetemplates;

import com.pw.gwhcore1.model.GwhRouteTemplate;
import com.pw.support1.configuration.GwhCoreProperties;
import com.pw.support1.configuration.GwhResource;
import com.pw.support1.configuration.GwhResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplateLoader extends GwhResourceLoader<GwhRouteTemplate> {

    public GwhRouteTemplateLoader(GwhCoreProperties gwhCoreProperties, GwhResource<GwhRouteTemplate> gwhResource) {
        super(gwhCoreProperties, gwhResource);
    }
}
