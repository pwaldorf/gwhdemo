package com.pw.jpa.routepropertiesloader1.configuration;

import com.pw.api1.configuration.*;
import com.pw.jpa.routepropertiesloader1.jpa.service.GwhRoutePropertiesService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.route.properties.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class JpaRoutePropertiesResource implements GwhRoutePropertiesResource {

    private final GwhRoutePropertiesService propertiesService;
    private final GwhRoutePropertyFactory gwhRoutePropertyFactory;

    public JpaRoutePropertiesResource(GwhRoutePropertiesService propertiesService, GwhRoutePropertyFactory gwhRoutePropertyFactory) {
        this.propertiesService = propertiesService;
        this.gwhRoutePropertyFactory = gwhRoutePropertyFactory;
    }

    @Override
    public List<GwhRouteProperty> getResourceAll() {
        return propertiesService.getAllProperties()
                .stream()
                .map(item -> gwhRoutePropertyFactory.createRouteProperty(item.getRouteId(), item.getPropertyKey(), item.getPropertyValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteProperty> getResourceByProfile(String profile) {
        return propertiesService.getByProfile(profile)
                .stream()
                .map(item -> gwhRoutePropertyFactory.createRouteProperty(item.getRouteId(), item.getPropertyKey(), item.getPropertyValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteProperty> getResourceByProfileAndRegion(String profile, String region) {
        return propertiesService.getByProfileAndRegion(profile, region)
                .stream()
                .map(item -> gwhRoutePropertyFactory.createRouteProperty(item.getRouteId(), item.getPropertyKey(), item.getPropertyValue()))
                .collect(Collectors.toList());
    }
}
