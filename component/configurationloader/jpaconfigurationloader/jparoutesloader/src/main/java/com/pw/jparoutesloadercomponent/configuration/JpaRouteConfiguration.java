package com.pw.jparoutesloadercomponent.configuration;

import com.pw.gwhcore.gwhroutes.GwhRouteResource;
import com.pw.gwhcore.model.GwhRoute;
import com.pw.jparoutesloadercomponent.jpa.service.GwhRouteService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jpa.enabled", havingValue = "true", matchIfMissing = false)
public class JpaRouteConfiguration implements GwhRouteResource {

    private final GwhRouteService gwhRouteService;

    public JpaRouteConfiguration(GwhRouteService gwhRouteService) {
        this.gwhRouteService = gwhRouteService;
    }

    @Override
    public List<GwhRoute> getResourceAll() {
        return gwhRouteService.getAllRoutes()
                .stream()
                .map(item -> new GwhRoute(item.getRouteId(), item.getRoute()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhRoute> getResourceByProfile(String profile) {
        return gwhRouteService.getByProfile(profile)
                .stream()
                .map(item -> new GwhRoute(item.getRouteId(), item.getRoute()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhRoute> getResourceByProfileAndRegion(String profile, String region) {
        return List.of();
    }
}
