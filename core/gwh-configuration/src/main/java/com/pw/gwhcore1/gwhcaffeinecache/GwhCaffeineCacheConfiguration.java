package com.pw.gwhcore1.gwhcaffeinecache;

import com.pw.api1.configuration.GwhCaffeineCacheResource;
import com.pw.api1.GwhBuilder;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import org.apache.camel.CamelContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

@Configuration
@ConditionalOnProperty(value = "gwh.framework.load.caches.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhCaffeineCacheConfiguration {

    private final CamelContext camelContext;
    private final ApplicationContext applicationContext;
    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final GwhCaffeineCacheResource gwhResource;

    public GwhCaffeineCacheConfiguration(CamelContext camelContext, ApplicationContext applicationContext,
                                         GwhConfigurationProperties gwhConfigurationProperties, @Nullable GwhCaffeineCacheResource gwhResource) {
        this.camelContext = camelContext;
        this.applicationContext = applicationContext;
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.gwhResource = gwhResource;
    }

    @Bean
    public GwhBuilder getGwhCaffeineCacheBuilder() {
        return new GwhCaffeineCacheBuilder(gwhConfigurationProperties, camelContext, applicationContext, new GwhDefaultResourceLoader<>(gwhResource));
    }

}
