package com.pw.jpa.routepropertiesloader1.configuration;

import com.pw.api1.configuration.GwhPropertiesResource;
import com.pw.api1.configuration.GwhProperty;
import com.pw.api1.configuration.GwhPropertyFactory;
import com.pw.jpa.routepropertiesloader1.jpa.service.GwhRoutePropertiesService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.route.properties.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class JpaRoutePropertiesResource implements GwhPropertiesResource {

    private final GwhRoutePropertiesService propertiesService;
    private final GwhPropertyFactory gwhPropertyFactory;

    public JpaRoutePropertiesResource(GwhRoutePropertiesService propertiesService, GwhPropertyFactory gwhPropertyFactory) {
        this.propertiesService = propertiesService;
        this.gwhPropertyFactory = gwhPropertyFactory;
    }

    @Override
    public List<GwhProperty> getResourceAll() {
        return propertiesService.getAllProperties()
                .stream()
                .map(item -> gwhPropertyFactory.createProperty(item.getPropertyKey(), item.getPropertyValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhProperty> getResourceByProfile(String profile) {
        return propertiesService.getByProfile(profile)
                .stream()
                .map(item -> gwhPropertyFactory.createProperty(item.getPropertyKey(), item.getPropertyValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhProperty> getResourceByProfileAndRegion(String profile, String region) {
        return propertiesService.getByProfileAndRegion(profile, region)
                .stream()
                .map(item -> gwhPropertyFactory.createProperty(item.getPropertyKey(), item.getPropertyValue()))
                .collect(Collectors.toList());
    }
}
