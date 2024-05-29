package com.pw.gwhcore.gwhroutes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.CamelContext;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.spi.Resource;
// import org.apache.camel.support.PluginHelper;
import org.apache.camel.support.ResourceHelper;
import org.springframework.stereotype.Component;

import com.pw.gwhcore.gwhcaffeinecache.GwhConfigurationLoader;
import com.pw.gwhcore.model.GwhRoute;

@Component
public class GwhRoutesLoaderImpl implements GwhRoutesLoader {

    CamelContext camelContext;

    GwhConfigurationLoader<GwhRoute> gwhRoutes;

    public GwhRoutesLoaderImpl(CamelContext camelContext, GwhConfigurationLoader<GwhRoute> gwhRoutes) {
        this.camelContext = camelContext;
        this.gwhRoutes = gwhRoutes;
    }

    @Override
    public void loadRoutes() {

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
