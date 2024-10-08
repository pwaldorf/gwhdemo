package com.pw.jpa.routetemplateloader1.jpa.service;

import java.util.ArrayList;
import java.util.List;

import com.pw.jpa.routetemplateloader1.jpa.model.GwhRouteTemplateEntity;
import com.pw.jpa.routetemplateloader1.jpa.repository.GwhRouteTemplateRepository;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;


@Service
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplateService {

    private final GwhRouteTemplateRepository routeTemplateRepository;

    public GwhRouteTemplateService(GwhRouteTemplateRepository routeTemplateRepository) {
        this.routeTemplateRepository = routeTemplateRepository;
    }

    public List<GwhRouteTemplateEntity> getAllRouteTemplates() {
        List<GwhRouteTemplateEntity> routeTemplates = routeTemplateRepository.findAll();

        if(CollectionUtils.isNotEmpty(routeTemplates)) {
            return routeTemplates;
        } else {
            return new ArrayList<GwhRouteTemplateEntity>();
        }
    }

    public List<GwhRouteTemplateEntity> getByProfile(String profile) {
        List<GwhRouteTemplateEntity> routeTemplates = routeTemplateRepository.findByProfile(profile);

        if(CollectionUtils.isNotEmpty(routeTemplates)) {
            return routeTemplates;
        } else {
            return new ArrayList<GwhRouteTemplateEntity>();
        }
    }

    public List<GwhRouteTemplateEntity> getByProfileAndRegionAndVersion(String profile, String region, String version) {
        List<GwhRouteTemplateEntity> routeTemplates = routeTemplateRepository.findByProfileAndRegionAndVersion(profile, region, version);

        if(CollectionUtils.isNotEmpty(routeTemplates)) {
            return routeTemplates;
        } else {
            return new ArrayList<GwhRouteTemplateEntity>();
        }
    }
}
