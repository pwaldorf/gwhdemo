package com.pw.gwhcore1.gwhroutes;

import com.pw.api1.configuration.GwhRoute;
import com.pw.api1.GwhBuilder;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class GwhRouteBuilder implements GwhBuilder {

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final CamelContext camelContext;
    private final GwhDefaultResourceLoader<GwhRoute> gwhRoutes;

    public GwhRouteBuilder(GwhConfigurationProperties gwhConfigurationProperties, CamelContext camelContext, GwhDefaultResourceLoader<GwhRoute> gwhRoutes) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.camelContext = camelContext;
        this.gwhRoutes = gwhRoutes;
    }

    public void build() {

        List<Resource> resourceList = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        gwhRoutes.getResource(gwhConfigurationProperties).forEach(routeEntity
                -> resourceList.add(ResourceHelper.fromString("MyRoute" + atomicInteger.incrementAndGet() +
                ".xml", routeEntity.getRoute())));

        try {
            ExtendedCamelContext extendedCamelContext = camelContext.adapt(ExtendedCamelContext.class);
            extendedCamelContext.getRoutesLoader().loadRoutes(resourceList);
            // PluginHelper.getRoutesLoader(camelContext.getCamelContextExtension()).loadRoutes(resourceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
