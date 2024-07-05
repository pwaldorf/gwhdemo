package com.pw.mybatisroutetemplatesloader1.configuration;

import java.util.List;
import java.util.stream.Collectors;
import com.pw.api1.configuration.GwhRouteTemplateFactory;
import com.pw.mybatisroutetemplatesloader1.mybatis.mapper.GwhRouteTemplateMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.api1.GwhResource;
import com.pw.api1.configuration.GwhRouteTemplate;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.mybatis1.enabled", havingValue = "true", matchIfMissing = false)
public class MyBatisRouteTemplatesResource implements GwhResource<GwhRouteTemplate> {

    private final GwhRouteTemplateMapper gwhRouteTemplateMapper;
    private final GwhRouteTemplateFactory gwhRouteTemplateFactory;

    public MyBatisRouteTemplatesResource(GwhRouteTemplateMapper gwhRouteTemplateMapper, GwhRouteTemplateFactory gwhRouteTemplateFactory) {
        this.gwhRouteTemplateMapper = gwhRouteTemplateMapper;
        this.gwhRouteTemplateFactory = gwhRouteTemplateFactory;
    }

    @Override
    public List<GwhRouteTemplate> getResourceAll() {
        return gwhRouteTemplateMapper.getAllRouteTemplates()
                .stream()
                .map(item -> gwhRouteTemplateFactory.createRouteTemplate(item.getRouteId(),
                        item.getTemplateParamName(),
                        item.getTemplateParamValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteTemplate> getResourceByProfile(String profile) {
        return gwhRouteTemplateMapper.getByProfile(profile)
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
