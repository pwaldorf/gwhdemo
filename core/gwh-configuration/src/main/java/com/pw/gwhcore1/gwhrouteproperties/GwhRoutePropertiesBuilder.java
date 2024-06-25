package com.pw.gwhcore1.gwhrouteproperties;

import com.pw.api1.configuration.GwhRouteProperty;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import com.pw.api1.GwhLoader;
import org.apache.camel.CamelContext;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;

import java.util.*;

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

        if (MapUtils.isNotEmpty(map)) {
            camelContext.getRoutes().forEach(route -> Optional.ofNullable(map.get(route.getRouteId())).orElse(Collections.emptyList()).forEach(
                    routeProperty -> route.getProperties().putIfAbsent(routeProperty.getKey(), routeProperty.getValue())));
        }

    }
}
