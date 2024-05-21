package com.pw.jdbcroutetemplatesloadercomponent.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pw.gwhcore.configurations.GwhCoreProperties;
import com.pw.gwhcore.gwhroutetemplates.GwhRouteTemplates;
import com.pw.gwhcore.model.GwhRouteTemplate;
import com.pw.jdbcroutetemplatesloadercomponent.jdbc.dao.GwhRouteTemplateDao;
import com.pw.jdbcroutetemplatesloadercomponent.jdbc.mapper.GwhRouteTemplateRowMapper;

//@Component
public class GwhRouteTemplatesConfiguration implements GwhRouteTemplates {

    private GwhCoreProperties gwhCoreProperties;
    private GwhRouteTemplateDao gwhRouteTemplateDao;

    public GwhRouteTemplatesConfiguration(GwhCoreProperties gwhCoreProperties, GwhRouteTemplateDao gwhRouteTemplateDao) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.gwhRouteTemplateDao = gwhRouteTemplateDao;
    }

    @Override
    public List<GwhRouteTemplate> getTemplatesByProfile() {

        List<GwhRouteTemplate> gwhRouteTemplates = gwhRouteTemplateDao.getRouteTemplatesByProfile(gwhCoreProperties.getProfile()).stream()
            .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
            .collect(Collectors.toList());

        return gwhRouteTemplates;

    }

    @Override
    public List<GwhRouteTemplate> getTemplatesByProfileAndRegion() {

        // List<GwhRouteTemplate> gwhRouteTemplates = (gwhRouteTemplateService.getByProfile(gwhCoreProperties.getProfile()))
        //      .stream()
        //      .map(item -> new GwhRouteTemplateModel(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
        //      .collect(Collectors.toList());

        // return gwhRouteTemplates;
        return null;

    }

    @Override
    public List<GwhRouteTemplate> getAllTemplates() {

        List<GwhRouteTemplate> gwhRouteTemplates = gwhRouteTemplateDao.getAllRouteTemplates().stream()
            .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
            .collect(Collectors.toList());

        return gwhRouteTemplates;
    }

}
