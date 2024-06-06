package com.pw.jpa.routetemplateloader1.jpa.service;

import java.util.ArrayList;
import java.util.List;

import com.pw.jpa.routetemplateloader1.jpa.model.GwhRouteTemplateEntity;
import com.pw.jpa.routetemplateloader1.jpa.repository.GwhRouteTemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.jpa1.enabled", havingValue = "true", matchIfMissing = false)
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
