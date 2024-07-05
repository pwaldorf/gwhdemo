package com.pw.gwhcore1.gwhrouteproperties;

import com.pw.api1.configuration.GwhRouteProperty;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.api1.GwhLoader;
import com.pw.api1.GwhResource;

import org.apache.camel.CamelContext;
import org.apache.commons.collections4.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.load.routes.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRoutePropertiesBuilder implements GwhLoader {

    private GwhResource<GwhRouteProperty> gwhResource;

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final CamelContext camelContext;

    public GwhRoutePropertiesBuilder(GwhConfigurationProperties gwhConfigurationProperties, CamelContext camelContext) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.camelContext = camelContext;
    }

    public void load() {

        Map<String, List<GwhRouteProperty>> map = new HashMap<>();
        gwhResource.getResource(gwhConfigurationProperties.getProfile(), gwhConfigurationProperties.getRegion(), gwhConfigurationProperties.getVersion())
            .forEach(routeProperty -> map.computeIfAbsent(routeProperty.getRouteId(), k -> new ArrayList<>()).add(routeProperty));

        if (MapUtils.isNotEmpty(map)) {
            camelContext.getRoutes().forEach(route -> Optional.ofNullable(map.get(route.getRouteId())).orElse(Collections.emptyList()).forEach(
                    routeProperty -> route.getProperties().putIfAbsent(routeProperty.getKey(), routeProperty.getValue())));
        }
    }

    @Autowired
    public void setGwhResource(GwhResource<GwhRouteProperty> gwhResource) {
        this.gwhResource = gwhResource;
    }
}
