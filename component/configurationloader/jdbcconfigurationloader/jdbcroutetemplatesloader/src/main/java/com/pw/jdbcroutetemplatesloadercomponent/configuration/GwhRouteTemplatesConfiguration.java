package com.pw.jdbcroutetemplatesloadercomponent.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.gwhcore.configurations.GwhCoreProperties;
import com.pw.gwhcore.gwhcaffeinecache.GwhConfigurationLoader;
import com.pw.gwhcore.model.GwhRouteTemplate;
import com.pw.jdbcroutetemplatesloadercomponent.jdbc.dao.GwhRouteTemplateDao;

@Component
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jdbc.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplatesConfiguration implements GwhConfigurationLoader<GwhRouteTemplate> {

    private GwhCoreProperties gwhCoreProperties;
    private GwhRouteTemplateDao gwhRouteTemplateDao;

    public GwhRouteTemplatesConfiguration(GwhCoreProperties gwhCoreProperties, GwhRouteTemplateDao gwhRouteTemplateDao) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.gwhRouteTemplateDao = gwhRouteTemplateDao;
    }

    @Override
    public List<GwhRouteTemplate> getByProfile() {

        List<GwhRouteTemplate> gwhRouteTemplates = gwhRouteTemplateDao.getRouteTemplatesByProfile(gwhCoreProperties.getProfile()).stream()
            .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
            .collect(Collectors.toList());

        return gwhRouteTemplates;

    }

    @Override
    public List<GwhRouteTemplate> getByProfileAndRegion() {

        // List<GwhRouteTemplate> gwhRouteTemplates = (gwhRouteTemplateService.getByProfile(gwhCoreProperties.getProfile()))
        //      .stream()
        //      .map(item -> new GwhRouteTemplateModel(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
        //      .collect(Collectors.toList());

        // return gwhRouteTemplates;
        return null;

    }

    @Override
    public List<GwhRouteTemplate> getAll() {

        List<GwhRouteTemplate> gwhRouteTemplates = gwhRouteTemplateDao.getAllRouteTemplates().stream()
            .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
            .collect(Collectors.toList());

        return gwhRouteTemplates;
    }

}
