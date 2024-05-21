package com.pw.jparoutetemplatesloadercomponent.jpa.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.pw.jparoutetemplatesloadercomponent.jpa.model.GwhRouteTemplateEntity;
import com.pw.jparoutetemplatesloadercomponent.jpa.repository.GwhRouteTemplateRepository;


@Service
@ConditionalOnProperty(value = "gwh.framework.routes.load.params.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplateService {

    @Autowired
    GwhRouteTemplateRepository routeTemplateRepository;

    public List<GwhRouteTemplateEntity> getAllRouteTemplates() {
        List<GwhRouteTemplateEntity> routeTemplates = routeTemplateRepository.findAll();

        if(routeTemplates.size() > 0) {
            return routeTemplates;
        } else {
            return new ArrayList<GwhRouteTemplateEntity>();
        }
    }

    public List<GwhRouteTemplateEntity> getByProfile(String profile) {
        List<GwhRouteTemplateEntity> routeTemplates = routeTemplateRepository.findByProfile(profile);

        if(routeTemplates.size() > 0) {
            return routeTemplates;
        } else {
            return new ArrayList<GwhRouteTemplateEntity>();
        }
    }
}
