package com.pw.jpa.routetemplateloader1.configuration;

import com.pw.api1.configuration.GwhRouteTemplate;
import com.pw.api1.configuration.GwhRouteTemplateFactory;
import com.pw.api1.configuration.GwhRouteTemplateResource;
import com.pw.jpa.routetemplateloader1.jpa.service.GwhRouteTemplateService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class JpaRouteTemplateResource implements GwhRouteTemplateResource {

    private final GwhRouteTemplateService gwhRouteTemplateService;
    private final GwhRouteTemplateFactory gwhRouteTemplateFactory;

    public JpaRouteTemplateResource(GwhRouteTemplateService gwhRouteTemplateService, GwhRouteTemplateFactory gwhRouteTemplateFactory) {
        this.gwhRouteTemplateService = gwhRouteTemplateService;
        this.gwhRouteTemplateFactory = gwhRouteTemplateFactory;
    }

    @Override
    public List<GwhRouteTemplate> getResourceAll() {
        return gwhRouteTemplateService.getAllRouteTemplates()
             .stream()
             .map(item -> gwhRouteTemplateFactory.createRouteTemplate(item.getRouteId(),
                     item.getTemplateParamName(),
                     item.getTemplateParamValue()))
             .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteTemplate> getResourceByProfile(String profile) {
        return gwhRouteTemplateService.getByProfile(profile)
             .stream()
             .map(item -> gwhRouteTemplateFactory.createRouteTemplate(item.getRouteId(),
                     item.getTemplateParamName(),
                     item.getTemplateParamValue()))
             .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteTemplate> getResourceByProfileAndRegion(String profile, String region) {
        return List.of();
    }
}
