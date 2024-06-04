package com.pw.gwhcore.gwhproperties;

import com.pw.gwhcore.model.GwhProperty;
import com.pw.support.configuration.GwhCoreProperties;
import com.pw.support.configuration.GwhResource;
import com.pw.support.configuration.GwhResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhPropertiesLoader extends GwhResourceLoader<GwhProperty> {
    public GwhPropertiesLoader(GwhCoreProperties gwhCoreProperties, GwhResource<GwhProperty> gwhResource) {
        super(gwhCoreProperties, gwhResource);
    }
}
