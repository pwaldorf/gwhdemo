package com.pw.gwhcore1.gwhroutetemplates;

import com.pw.api1.configuration.GwhRouteTemplateResource;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.main.PropertiesRouteTemplateParametersSource;
import org.apache.camel.spi.RouteTemplateParameterSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

@Slf4j
@Configuration
@ConditionalOnProperty(value = "gwh.framework.load.routetemplates.core1.enabled", havingValue = "true", matchIfMissing = false)
@ConditionalOnBean(GwhConfigurationProperties.class)
public class GwhRouteTemplateConfiguration {

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final GwhRouteTemplateResource gwhResource;

    public GwhRouteTemplateConfiguration(GwhConfigurationProperties gwhConfigurationProperties, @Nullable GwhRouteTemplateResource gwhResource) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.gwhResource = gwhResource;
    }

    @Bean
    public RouteTemplateParameterSource getRouteTemplates() {
        PropertiesRouteTemplateParametersSource source = new PropertiesRouteTemplateParametersSource();

        log.debug("Adding new routes from template params");
        new GwhDefaultResourceLoader<>(gwhResource).getResource(gwhConfigurationProperties)
                .forEach(routeTemplate -> {source.addParameter(routeTemplate.getRouteId(),
                        routeTemplate.getTemplateParamName(), routeTemplate.getTemplateParamValue());
        });

        return source;
    }

}
