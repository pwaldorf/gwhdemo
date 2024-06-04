package com.pw.mybatisroutetemplatesloader.configuration;

import java.util.List;
import java.util.stream.Collectors;

import com.pw.gwhcore.gwhroutetemplates.GwhRouteTemplateResource;
import com.pw.mybatisroutetemplatesloader.mybatis.mapper.GwhRouteTemplateMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.gwhcore.model.GwhRouteTemplate;

@Component
@ConditionalOnProperty(value = "gwh.framework.configuration.loader.mybatis.enabled", havingValue = "true", matchIfMissing = false)
public class MyBatisRouteTemplatesConfiguration implements GwhRouteTemplateResource {

    private final GwhRouteTemplateMapper gwhRouteTemplateMapper;

    public MyBatisRouteTemplatesConfiguration(GwhRouteTemplateMapper gwhRouteTemplateMapper) {
        this.gwhRouteTemplateMapper = gwhRouteTemplateMapper;
    }

    @Override
    public List<GwhRouteTemplate> getResourceAll() {
        return gwhRouteTemplateMapper.getAllRouteTemplates()
                .stream()
                .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteTemplate> getResourceByProfile(String profile) {
        return gwhRouteTemplateMapper.getByProfile(profile)
             .stream()
             .map(item -> new GwhRouteTemplate(item.getRouteId(), item.getTemplateParamName(), item.getTemplateParamValue()))
             .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteTemplate> getResourceByProfileAndRegion(String profile, String region) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTemplatesByProfileAndRegion'");
    }

}
