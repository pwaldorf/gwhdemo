package com.pw.gwhcore.configurations;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pw.gwhcore.jpa.model.GwhRouteEntity;
import com.pw.gwhcore.jpa.model.GwhRouteTemplateEntity;
import com.pw.gwhcore.jpa.service.GwhRouteService;
import com.pw.gwhcore.jpa.service.GwhRouteTemplateService;

@Configuration
public class GwhCoreConfigs {

    @Value("${gwh.service.profile}")
    private String profile;

    @Autowired
    private GwhRouteService routeService;

    @Autowired
    private GwhRouteTemplateService routeTemplateService;
    
    @Bean("gwhRouteEntities")
    public List<GwhRouteEntity> gwhRouteEntities() {        
        return routeService.getByProfile(profile);
    }

    @Bean("gwhRouteTemplateEntities")
    public List<GwhRouteTemplateEntity> gwhRouteTemplateEntities() {        
        return routeTemplateService.getByProfile(profile);
    }

}
