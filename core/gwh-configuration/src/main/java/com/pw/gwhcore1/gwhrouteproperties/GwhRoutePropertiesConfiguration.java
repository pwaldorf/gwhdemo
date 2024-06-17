package com.pw.gwhcore1.gwhrouteproperties;

import com.pw.api1.configuration.GwhRoutePropertiesResource;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import com.pw.api1.GwhLoader;
import org.apache.camel.CamelContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.load.routes.core1.enabled", havingValue = "true", matchIfMissing = false)
@ConditionalOnBean(GwhConfigurationProperties.class)
public class GwhRoutePropertiesConfiguration {

    private final CamelContext camelContext;
    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final GwhRoutePropertiesResource gwhResource;

    public GwhRoutePropertiesConfiguration(CamelContext camelContext, GwhConfigurationProperties gwhConfigurationProperties, @Nullable GwhRoutePropertiesResource gwhResource) {
        this.camelContext = camelContext;
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.gwhResource = gwhResource;
    }

    @Bean
    public GwhLoader gwhRouteProperties() {
        return new GwhRoutePropertiesBuilder(gwhConfigurationProperties, camelContext, new GwhDefaultResourceLoader<>(gwhResource));
    }

}
