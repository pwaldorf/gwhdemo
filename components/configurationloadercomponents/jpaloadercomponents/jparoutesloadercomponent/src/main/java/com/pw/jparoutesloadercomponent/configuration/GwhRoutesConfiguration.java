package com.pw.jparoutesloadercomponent.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pw.gwhcore.configurations.GwhCoreProperties;
import com.pw.gwhcore.gwhroutes.GwhRoutes;
import com.pw.gwhcore.model.GwhRoute;
import com.pw.jparoutesloadercomponent.jpa.service.GwhRouteService;

@Component
public class GwhRoutesConfiguration implements GwhRoutes {

    private GwhCoreProperties gwhCoreProperties;
    private GwhRouteService gwhRouteService;

    public GwhRoutesConfiguration(GwhCoreProperties gwhCoreProperties, GwhRouteService gwhRouteService) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.gwhRouteService = gwhRouteService;
    }

    @Override
    public List<GwhRoute> getRoutes() {

        List<GwhRoute> gwhRoutes = (gwhRouteService.getByProfile(gwhCoreProperties.getProfile()))
             .stream()
             .map(item -> new GwhRoute(item.getRouteId(), item.getRoute()))
             .collect(Collectors.toList());

        return gwhRoutes;
    }

}
