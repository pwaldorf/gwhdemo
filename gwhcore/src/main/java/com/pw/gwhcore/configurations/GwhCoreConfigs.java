package com.pw.gwhcore.configurations;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pw.gwhcore.jpa.model.GwhRouteEntity;
import com.pw.gwhcore.jpa.model.GwhRouteTemplateEntity;
import com.pw.gwhcore.jpa.service.GwhRouteService;
import com.pw.gwhcore.jpa.service.GwhRouteTemplateService;

@Configuration
public class GwhCoreConfigs {

    private GwhCoreProperties gwhCoreProperties;

    private GwhRouteService routeService;

    private GwhRouteTemplateService routeTemplateService;

    public GwhCoreConfigs(GwhCoreProperties gwhCoreProperties, GwhRouteService routeService, GwhRouteTemplateService routeTemplateService) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.routeService = routeService;
        this.routeTemplateService = routeTemplateService;
    }

    @Bean("gwhRouteEntities")
    public List<GwhRouteEntity> gwhRouteEntities() {
        return routeService.getByProfile(gwhCoreProperties.getProfile());
    }

    @Bean("gwhRouteTemplateEntities")
    public List<GwhRouteTemplateEntity> gwhRouteTemplateEntities() {
        return routeTemplateService.getByProfile(gwhCoreProperties.getProfile());
    }

}
