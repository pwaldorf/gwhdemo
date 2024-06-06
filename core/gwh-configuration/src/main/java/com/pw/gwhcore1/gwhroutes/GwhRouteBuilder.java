package com.pw.gwhcore1.gwhroutes;

import com.pw.gwhcore1.model.GwhRoute;
import com.pw.support1.configuration.GwhBuilder;
import com.pw.support1.configuration.GwhLoader;
import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.routes.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteBuilder implements GwhBuilder {

    private final CamelContext camelContext;

    private final GwhLoader<GwhRoute> gwhRoutes;

    public GwhRouteBuilder(CamelContext camelContext, GwhLoader<GwhRoute> gwhRoutes) {
        this.camelContext = camelContext;
        this.gwhRoutes = gwhRoutes;
    }

    public void build() {

        List<Resource> resourceList = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        gwhRoutes.getAll().stream().forEach(routeEntity
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
