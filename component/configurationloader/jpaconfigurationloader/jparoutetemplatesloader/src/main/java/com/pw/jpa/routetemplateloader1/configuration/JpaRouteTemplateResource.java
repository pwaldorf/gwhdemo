package com.pw.jpa.routetemplateloader1.configuration;

import com.pw.api1.GwhProfileResource;
import com.pw.api1.configuration.GwhRouteTemplate;
import com.pw.api1.configuration.GwhRouteTemplateFactory;
import com.pw.jpa.routetemplateloader1.jpa.service.GwhRouteTemplateService;
import com.pw.utils1.keywordresolver.ResolverParser;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.jpa1.enabled", havingValue = "true", matchIfMissing = false)
public class JpaRouteTemplateResource implements GwhProfileResource<GwhRouteTemplate> {

    private final GwhRouteTemplateService gwhRouteTemplateService;
    private final GwhRouteTemplateFactory gwhRouteTemplateFactory;
    private final ResolverParser resolverParser;

    public JpaRouteTemplateResource(GwhRouteTemplateService gwhRouteTemplateService, GwhRouteTemplateFactory gwhRouteTemplateFactory, ResolverParser resolverParser) {
        this.gwhRouteTemplateService = gwhRouteTemplateService;
        this.gwhRouteTemplateFactory = gwhRouteTemplateFactory;
        this.resolverParser = resolverParser;
    }

    @Override
    public List<GwhRouteTemplate> getResourceAll() {
        return gwhRouteTemplateService.getAllRouteTemplates()
             .stream()
             .map(item -> {
                item.setTemplateParamValue(resolverParser.resolveKeywords(item.getTemplateParamValue()));
                return item;
             })
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
    public List<GwhRouteTemplate> getResourceByProfileAndRegionAndVersion(String profile, String region, String version) {
        return List.of();
    }
}
