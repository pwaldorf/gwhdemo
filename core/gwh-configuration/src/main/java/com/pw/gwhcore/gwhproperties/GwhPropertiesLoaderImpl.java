package com.pw.gwhcore.gwhproperties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class GwhPropertiesLoaderImpl implements ApplicationListener<ContextRefreshedEvent> {


    private final GwhProperties gwhProperties;

    private final ConfigurableEnvironment configurableEnvironment;

    public GwhPropertiesLoaderImpl(ConfigurableEnvironment configurableEnvironment, GwhProperties gwhProperties) {
        this.configurableEnvironment = configurableEnvironment;
        this.gwhProperties = gwhProperties;
    }

    @Override
    public void onApplicationEvent(@SuppressWarnings("null") ContextRefreshedEvent event) {

        Map<String, Object> propertySource = new HashMap<>();
        gwhProperties.getProperties().forEach((key, value) -> propertySource.put((String) key, value));
        log.info("Loaded configurations");
        configurableEnvironment.getPropertySources().addFirst(new MapPropertySource("database", propertySource));

    }

}
