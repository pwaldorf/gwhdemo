package com.pw.gwhcore1.gwhproperties;

import java.util.HashMap;
import java.util.Map;

import com.pw.api1.configuration.GwhProperty;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GwhPropertiesBuilder implements ApplicationListener<ContextRefreshedEvent> {

    private final GwhConfigurationProperties gwhConfigurationProperties;
    private final GwhDefaultResourceLoader<GwhProperty> gwhProperties;
    private final ConfigurableEnvironment configurableEnvironment;

    public GwhPropertiesBuilder(GwhConfigurationProperties gwhConfigurationProperties,
                                ConfigurableEnvironment configurableEnvironment,
                                GwhDefaultResourceLoader<GwhProperty> gwhProperties) {
        this.gwhConfigurationProperties = gwhConfigurationProperties;
        this.configurableEnvironment = configurableEnvironment;
        this.gwhProperties = gwhProperties;
    }

    @Override
    public void onApplicationEvent(@SuppressWarnings("null") ContextRefreshedEvent event) {

        Map<String, Object> propertySource = new HashMap<>();
        gwhProperties.getResource(gwhConfigurationProperties).forEach(property -> propertySource.put(property.getKey(), property.getValue()));
        log.info("Loaded configurations");
        configurableEnvironment.getPropertySources().addFirst(new MapPropertySource("database", propertySource));

    }

}
