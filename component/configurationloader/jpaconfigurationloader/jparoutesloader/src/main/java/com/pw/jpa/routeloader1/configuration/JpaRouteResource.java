package com.pw.jpa.routeloader1.configuration;

import com.pw.api1.configuration.GwhRouteFactory;
import com.pw.api1.GwhProfileResource;
import com.pw.api1.configuration.GwhRoute;
import com.pw.jpa.routeloader1.jpa.service.GwhRouteService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.routes.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class JpaRouteResource implements GwhProfileResource<GwhRoute> {

    private final GwhRouteService gwhRouteService;
    private final GwhRouteFactory gwhRouteFactory;

    public JpaRouteResource(GwhRouteService gwhRouteService, GwhRouteFactory gwhRouteFactory) {
        this.gwhRouteService = gwhRouteService;
        this.gwhRouteFactory = gwhRouteFactory;
    }

    @Override
    public List<GwhRoute> getResourceAll() {
        return gwhRouteService.getAllRoutes()
                .stream()
                .map(item -> gwhRouteFactory.createRoute(item.getRouteId(), item.getRouteType(), item.getRoute()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhRoute> getResourceByProfile(String profile) {
        return gwhRouteService.getByProfile(profile)
                .stream()
                .map(item -> gwhRouteFactory.createRoute(item.getRouteId(), item.getRouteType(), item.getRoute()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhRoute> getResourceByProfileAndRegionAndVersion(String profile, String region, String version) {
        return List.of();
    }
}
