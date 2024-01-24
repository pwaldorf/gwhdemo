package com.pw.gwhcore.jpa.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.pw.gwhcore.jpa.model.RouteTemplateEntity;
import com.pw.gwhcore.jpa.repository.RouteTemplateRepository;


@Service
@ConditionalOnProperty(value = "gtw.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class RouteTemplateService {
    
    @Autowired
    RouteTemplateRepository routeTemplateRepository;

    public List<RouteTemplateEntity> getAllRouteTemplates() {
        List<RouteTemplateEntity> routeTemplates = routeTemplateRepository.findAll();

        if(routeTemplates.size() > 0) {
            return routeTemplates;
        } else {
            return new ArrayList<RouteTemplateEntity>();
        }              
    }

    public List<RouteTemplateEntity> getByProfile(String profile) {
        List<RouteTemplateEntity> routeTemplates = routeTemplateRepository.findByProfile(profile);

        if(routeTemplates.size() > 0) {
            return routeTemplates;
        } else {
            return new ArrayList<RouteTemplateEntity>();
        }              
    }
}
