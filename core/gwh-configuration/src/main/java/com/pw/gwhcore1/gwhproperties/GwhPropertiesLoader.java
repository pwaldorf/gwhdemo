package com.pw.gwhcore1.gwhproperties;

import com.pw.gwhcore1.model.GwhProperty;
import com.pw.support1.configuration.GwhCoreProperties;
import com.pw.support1.configuration.GwhResource;
import com.pw.support1.configuration.GwhResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.properties.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhPropertiesLoader extends GwhResourceLoader<GwhProperty> {
    public GwhPropertiesLoader(GwhCoreProperties gwhCoreProperties, GwhResource<GwhProperty> gwhResource) {
        super(gwhCoreProperties, gwhResource);
    }
}
