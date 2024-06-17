package com.pw.gwhcore1.gwhroutes;

import com.pw.api1.configuration.GwhRouteResource;
import com.pw.api1.GwhBuilder;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import org.apache.camel.CamelContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.load.routes.core1.enabled", havingValue = "true", matchIfMissing = false)
@ConditionalOnBean(GwhConfigurationProperties.class)
public class GwhRouteConfiguration {

    private final CamelContext camelContext;
    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final GwhRouteResource gwhResource;

    public GwhRouteConfiguration(GwhConfigurationProperties gwhConfigurationProperties, CamelContext camelContext, @Nullable GwhRouteResource gwhResource) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.camelContext = camelContext;
        this.gwhResource = gwhResource;
    }

    @Bean
    public GwhBuilder getGwhRouteBuilder() {
        return new GwhRouteBuilder(gwhConfigurationProperties, camelContext, new GwhDefaultResourceLoader<>(gwhResource));
    }

}
