package com.pw.jparoutetemplatesloadercomponent.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.pw.gwhcore.configurations.GwhCoreProperties;
import com.pw.gwhcore.gwhroutetemplates.GwhRouteTemplates;
import com.pw.gwhcore.model.GwhRouteTemplate;
import com.pw.jparoutetemplatesloadercomponent.jpa.service.GwhRouteTemplateService;

@Component
public class GwhRouteTemplatesConfiguration implements GwhRouteTemplates {

    private GwhCoreProperties gwhCoreProperties;
    private GwhRouteTemplateService gwhRouteTemplateService;

    public GwhRouteTemplatesConfiguration(GwhCoreProperties gwhCoreProperties, GwhRouteTemplateService gwhRouteTemplateService) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.gwhRouteTemplateService = gwhRouteTemplateService;
    }

    @Override
    public List<GwhRouteTemplate> getTemplates() {

        List<GwhRouteTemplate> gwhRouteTemplates = (gwhRouteTemplateService.getByProfile(gwhCoreProperties.getProfile()))
             .stream()
             .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
             .collect(Collectors.toList());

        return gwhRouteTemplates;

    }

}
