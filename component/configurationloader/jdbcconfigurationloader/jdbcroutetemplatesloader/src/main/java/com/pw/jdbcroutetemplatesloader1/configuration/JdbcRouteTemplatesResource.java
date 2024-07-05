package com.pw.jdbcroutetemplatesloader1.configuration;

import java.util.List;
import java.util.stream.Collectors;

import com.pw.api1.configuration.GwhRouteTemplateFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.pw.api1.GwhResource;
import com.pw.api1.configuration.GwhRouteTemplate;
import com.pw.jdbcroutetemplatesloader1.jdbc.dao.GwhRouteTemplateDao;

@Component
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.jdbc1.enabled", havingValue = "true", matchIfMissing = false)
public class JdbcRouteTemplatesResource implements GwhResource<GwhRouteTemplate> {

    private final GwhRouteTemplateDao gwhRouteTemplateDao;
    private final GwhRouteTemplateFactory gwhRouteTemplateFactory;

    public JdbcRouteTemplatesResource(GwhRouteTemplateDao gwhRouteTemplateDao, GwhRouteTemplateFactory gwhRouteTemplateFactory) {
        this.gwhRouteTemplateDao = gwhRouteTemplateDao;
        this.gwhRouteTemplateFactory = gwhRouteTemplateFactory;
    }

    @Override
    public List<GwhRouteTemplate> getResourceAll() {

        return gwhRouteTemplateDao.getAllRouteTemplates()
                .stream()
                .map(item -> gwhRouteTemplateFactory.createRouteTemplate(item.getRouteId(),
                        item.getTemplateParamName(),
                        item.getTemplateParamValue()))
                .collect(Collectors.toList());
    }

    @Override
    public List<GwhRouteTemplate> getResourceByProfile(String profile) {

        return gwhRouteTemplateDao.getRouteTemplatesByProfile(profile)
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
