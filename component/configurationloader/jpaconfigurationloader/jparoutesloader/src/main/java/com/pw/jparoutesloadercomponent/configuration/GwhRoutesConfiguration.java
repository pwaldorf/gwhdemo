package com.pw.jparoutesloadercomponent.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.gwhcore.configurations.GwhCoreProperties;
import com.pw.gwhcore.gwhcaffeinecache.GwhConfigurationLoader;
import com.pw.gwhcore.model.GwhRoute;
import com.pw.jparoutesloadercomponent.jpa.service.GwhRouteService;

@Component
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jpa.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRoutesConfiguration implements GwhConfigurationLoader<GwhRoute> {

    private GwhCoreProperties gwhCoreProperties;
    private GwhRouteService gwhRouteService;

    public GwhRoutesConfiguration(GwhCoreProperties gwhCoreProperties, GwhRouteService gwhRouteService) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.gwhRouteService = gwhRouteService;
    }

    @Override
    public List<GwhRoute> getAll() {

        List<GwhRoute> gwhRoutes = (gwhRouteService.getByProfile(gwhCoreProperties.getProfile()))
             .stream()
             .map(item -> new GwhRoute(item.getRouteId(), item.getRoute()))
             .collect(Collectors.toList());

        return gwhRoutes;
    }

    @Override
    public List<GwhRoute> getByProfile() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByProfile'");
    }

    @Override
    public List<GwhRoute> getByProfileAndRegion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getByProfileAndRegion'");
    }

}
