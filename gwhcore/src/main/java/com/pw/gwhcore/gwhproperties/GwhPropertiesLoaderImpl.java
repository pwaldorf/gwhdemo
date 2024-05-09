package com.pw.gwhcore.gwhproperties;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;


import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class GwhPropertiesLoaderImpl implements GwhPropertiesLoader {

    private GwhProperties gwhProperties;

    private Environment env;

    public GwhPropertiesLoaderImpl(Environment env, GwhProperties gwhProperties) {
        this.env = env;
        this.gwhProperties = gwhProperties;
    }

    @PostConstruct
    @Override
    public void loadConfigurations() {

        MutablePropertySources propertySources = ((ConfigurableEnvironment) env).getPropertySources();
        Map<String, Object> propertySource = new HashMap<>();
        gwhProperties.getProperties().forEach((key, value) -> propertySource.put((String) key, value));
        log.info("Loaded configurations");
        propertySources.addFirst(new MapPropertySource("database", propertySource));
    }

}
