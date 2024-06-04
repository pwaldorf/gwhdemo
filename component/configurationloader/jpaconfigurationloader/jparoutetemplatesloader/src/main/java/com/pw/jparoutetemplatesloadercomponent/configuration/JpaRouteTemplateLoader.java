package com.pw.jparoutetemplatesloadercomponent.configuration;

import com.pw.gwhcore.gwhroutetemplates.GwhRouteTemplateResource;
import com.pw.gwhcore.model.GwhRouteTemplate;
import com.pw.jparoutetemplatesloadercomponent.jpa.service.GwhRouteTemplateService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.jpa.enabled", havingValue = "true", matchIfMissing = false)
public class JpaRouteTemplateLoader implements GwhRouteTemplateResource {

    private final GwhRouteTemplateService gwhRouteTemplateService;

    public JpaRouteTemplateLoader(GwhRouteTemplateService gwhRouteTemplateService) {
        this.gwhRouteTemplateService = gwhRouteTemplateService;
    }

    @Override
    public List<GwhRouteTemplate> getResourceAll() {
        return gwhRouteTemplateService.getAllRouteTemplates()
             .stream()
             .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
             .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteTemplate> getResourceByProfile(String profile) {
        return gwhRouteTemplateService.getByProfile(profile)
             .stream()
             .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
             .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteTemplate> getResourceByProfileAndRegion(String profile, String region) {
        return List.of();
    }
}
