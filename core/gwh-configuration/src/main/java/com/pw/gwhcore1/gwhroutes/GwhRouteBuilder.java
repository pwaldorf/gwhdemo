package com.pw.gwhcore1.gwhroutes;

import com.pw.api1.configuration.GwhRoute;
import com.pw.api1.GwhBuilder;
import com.pw.api1.GwhProfileResource;
import com.pw.gwhcore1.GwhConfigurationProperties;
import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.routes.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteBuilder implements GwhBuilder {

    private GwhProfileResource<GwhRoute> gwhResource;

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final CamelContext camelContext;

    public GwhRouteBuilder(GwhConfigurationProperties gwhConfigurationProperties, CamelContext camelContext) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.camelContext = camelContext;
    }

    public void build() {

        if (gwhResource == null) {
            return;
        }

        List<Resource> resourceList = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        gwhResource.getResource(gwhConfigurationProperties.getProfile(), gwhConfigurationProperties.getRegion(), gwhConfigurationProperties.getVersion())
            .forEach(routeEntity -> resourceList.add(ResourceHelper.fromString("MyRoute" + atomicInteger.incrementAndGet() +
                ".xml", routeEntity.getRoute())));

        try {
            ExtendedCamelContext extendedCamelContext = camelContext.adapt(ExtendedCamelContext.class);
            extendedCamelContext.getRoutesLoader().loadRoutes(resourceList);
            // PluginHelper.getRoutesLoader(camelContext.getCamelContextExtension()).loadRoutes(resourceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public void setGwhResource(GwhProfileResource<GwhRoute> gwhResource) {
        this.gwhResource = gwhResource;
    }
}
