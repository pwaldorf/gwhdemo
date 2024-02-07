package com.pw.gwhcore.beans;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.PluginHelper;
import org.apache.camel.support.ResourceHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.pw.gwhcore.jpa.model.GwhRouteEntity;

public class GwhRoutesLoaderImpl implements GwhRoutesLoader {

    @Autowired
    @Qualifier("gwhRouteEntities")
    List<GwhRouteEntity> gwhRouteEntities;

    @Autowired
    CamelContext camelContext;

    @Override
    public void loadRoutes() {
        
        List<Resource> resourceList = new ArrayList<>();
        gwhRouteEntities.stream().forEach(routeEntity 
                -> resourceList.add(ResourceHelper.fromString("MyRoute.xml", routeEntity.getRoute())));

        try {
            PluginHelper.getRoutesLoader(camelContext.getCamelContextExtension()).loadRoutes(resourceList);                    
        } catch (Exception e) {                    
            e.printStackTrace();
        }
    }
    
}
