package com.pw.gwhcore1.gwhproperties;

import com.pw.api1.configuration.GwhPropertiesResource;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.lang.Nullable;

//@Configuration
//@ConditionalOnProperty(value = "gwh.framework.load.properties.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhPropertiesConfiguration {

    private final ConfigurableEnvironment configurableEnvironment;
    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final GwhPropertiesResource gwhResource;

    public GwhPropertiesConfiguration(ConfigurableEnvironment configurableEnvironment,
                                      GwhConfigurationProperties gwhConfigurationProperties,
                                      @Nullable GwhPropertiesResource gwhResource) {
        this.configurableEnvironment = configurableEnvironment;
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.gwhResource = gwhResource;
    }

    @Bean
    public ApplicationListener<ContextRefreshedEvent> getGwhPropertiesBuilder() {
        return new GwhPropertiesBuilder(gwhConfigurationProperties,
                                        configurableEnvironment,
                                        new GwhDefaultResourceLoader<>(gwhResource));
    }

}
