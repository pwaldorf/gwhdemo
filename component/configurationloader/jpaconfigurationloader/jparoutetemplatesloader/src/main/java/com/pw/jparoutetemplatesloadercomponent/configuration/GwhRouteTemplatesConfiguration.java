package com.pw.jparoutetemplatesloadercomponent.configuration;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.gwhcore.configurations.GwhCoreProperties;
import com.pw.gwhcore.gwhcaffeinecache.GwhConfigurationLoader;
import com.pw.gwhcore.model.GwhRouteTemplate;
import com.pw.jparoutetemplatesloadercomponent.jpa.service.GwhRouteTemplateService;

@Component
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jpa.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplatesConfiguration implements GwhConfigurationLoader<GwhRouteTemplate> {

    private GwhCoreProperties gwhCoreProperties;
    private GwhRouteTemplateService gwhRouteTemplateService;

    public GwhRouteTemplatesConfiguration(GwhCoreProperties gwhCoreProperties, GwhRouteTemplateService gwhRouteTemplateService) {
        this.gwhCoreProperties = gwhCoreProperties;
        this.gwhRouteTemplateService = gwhRouteTemplateService;
    }

    @Override
    public List<GwhRouteTemplate> getByProfile() {

        List<GwhRouteTemplate> gwhRouteTemplates = (gwhRouteTemplateService.getByProfile(gwhCoreProperties.getProfile()))
             .stream()
             .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
             .collect(Collectors.toList());

        return gwhRouteTemplates;

    }

    @Override
    public List<GwhRouteTemplate> getByProfileAndRegion() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTemplatesByProfileAndRegion'");
    }

    @Override
    public List<GwhRouteTemplate> getAll() {

        List<GwhRouteTemplate> gwhRouteTemplates = (gwhRouteTemplateService.getAllRouteTemplates())
             .stream()
             .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
             .collect(Collectors.toList());

        return gwhRouteTemplates;

    }

}
