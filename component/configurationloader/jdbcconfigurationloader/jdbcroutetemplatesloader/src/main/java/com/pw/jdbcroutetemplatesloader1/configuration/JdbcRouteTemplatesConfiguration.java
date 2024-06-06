package com.pw.jdbcroutetemplatesloader1.configuration;

import java.util.List;
import java.util.stream.Collectors;

import com.pw.gwhcore1.gwhroutetemplates.GwhRouteTemplateResource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.gwhcore1.model.GwhRouteTemplate;
import com.pw.jdbcroutetemplatesloader1.jdbc.dao.GwhRouteTemplateDao;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.jdbc1.enabled", havingValue = "true", matchIfMissing = false)
public class JdbcRouteTemplatesConfiguration implements GwhRouteTemplateResource {

    private final GwhRouteTemplateDao gwhRouteTemplateDao;

    public JdbcRouteTemplatesConfiguration(GwhRouteTemplateDao gwhRouteTemplateDao) {
        this.gwhRouteTemplateDao = gwhRouteTemplateDao;
    }

    @Override
    public List<GwhRouteTemplate> getResourceByProfile(String profile) {

        return gwhRouteTemplateDao.getRouteTemplatesByProfile(profile).stream()
            .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
            .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteTemplate> getResourceByProfileAndRegion(String profile, String region) {

        // List<GwhRouteTemplate> gwhRouteTemplates = (gwhRouteTemplateService.getByProfile(gwhCoreProperties.getProfile()))
        //      .stream()
        //      .map(item -> new GwhRouteTemplateModel(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
        //      .collect(Collectors.toList());

        // return gwhRouteTemplates;
        return null;

    }

    @Override
    public List<GwhRouteTemplate> getResourceAll() {

        return gwhRouteTemplateDao.getAllRouteTemplates()
            .stream()
            .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
            .collect(Collectors.toList());
    }

}
