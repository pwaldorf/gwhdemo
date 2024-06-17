package com.pw.gwhcore1.gwhrouteproperties;

import com.pw.api1.configuration.GwhRouteProperty;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import com.pw.api1.GwhLoader;
import org.apache.camel.CamelContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GwhRoutePropertiesBuilder implements GwhLoader {

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final CamelContext camelContext;
    private final GwhDefaultResourceLoader<GwhRouteProperty> gwhRouteProperties;

    public GwhRoutePropertiesBuilder(GwhConfigurationProperties gwhConfigurationProperties, CamelContext camelContext, GwhDefaultResourceLoader<GwhRouteProperty> gwhRouteProperties) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.camelContext = camelContext;
        this.gwhRouteProperties = gwhRouteProperties;
    }

    public void load() {

        Map<String, List<GwhRouteProperty>> map = new HashMap<>();
        gwhRouteProperties.getResource(gwhConfigurationProperties).forEach(routeProperty
                -> map.computeIfAbsent(routeProperty.getRouteId(), k -> new ArrayList<>()).add(routeProperty));

        camelContext.getRoutes().forEach( route -> map.get(route.getRouteId()).forEach(
                routeProperty -> route.getProperties().putIfAbsent(routeProperty.getKey(), routeProperty.getValue())));

    }
}
