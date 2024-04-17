package com.pw.gwhcore.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.PluginHelper;
import org.apache.camel.support.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pw.gwhcore.jpa.model.GwhRouteEntity;

@Component
public class GwhRoutesLoaderImpl implements GwhRoutesLoader {

    @Autowired
    @Qualifier("gwhRouteEntities")
    List<GwhRouteEntity> gwhRouteEntities;

    @Autowired
    CamelContext camelContext;

    @Override
    public void loadRoutes() {

        List<Resource> resourceList = new ArrayList<>();
        AtomicInteger atomicInteger = new AtomicInteger();
        gwhRouteEntities.stream().forEach(routeEntity
                -> resourceList.add(ResourceHelper.fromString("MyRoute" + atomicInteger.incrementAndGet() + ".xml", routeEntity.getRoute())));


        try {
            PluginHelper.getRoutesLoader(camelContext.getCamelContextExtension()).loadRoutes(resourceList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
