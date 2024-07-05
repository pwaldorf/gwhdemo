package com.pw.gwhcore1.gwhroutetemplates;

import com.pw.api1.GwhResource;
import com.pw.api1.configuration.GwhRouteTemplate;
import com.pw.gwhcore1.GwhConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.main.PropertiesRouteTemplateParametersSource;
import org.apache.camel.spi.RouteTemplateParameterSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhRouteTemplateConfiguration {

    private GwhResource<GwhRouteTemplate> gwhResource;

    private final GwhConfigurationProperties gwhConfigurationProperties;

    public GwhRouteTemplateConfiguration(GwhConfigurationProperties gwhConfigurationProperties) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
    }

    @Bean
    public RouteTemplateParameterSource getRouteTemplates() {
        PropertiesRouteTemplateParametersSource source = new PropertiesRouteTemplateParametersSource();

        if (gwhResource == null) {
            log.info("No Route Templates Defined");
            return source;
        }

        log.debug("Adding new routes from template params");
        gwhResource.getResource(gwhConfigurationProperties.getProfile(), gwhConfigurationProperties.getRegion(), gwhConfigurationProperties.getVersion())
                .forEach(routeTemplate -> {source.addParameter(routeTemplate.getRouteId(),
                        routeTemplate.getTemplateParamName(), routeTemplate.getTemplateParamValue());
        });

        return source;

    }

    @Autowired
    public void setGwhResource(GwhResource<GwhRouteTemplate> gwhResource) {
        this.gwhResource = gwhResource;
    }
}