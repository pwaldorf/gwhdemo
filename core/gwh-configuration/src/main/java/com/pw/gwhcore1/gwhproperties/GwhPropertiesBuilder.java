package com.pw.gwhcore1.gwhproperties;

import java.util.HashMap;
import java.util.Map;

import com.pw.gwhcore1.model.GwhProperty;
import com.pw.support1.configuration.GwhLoader;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@ConditionalOnProperty(value = "gwh.framework.load.properties.core1.enabled", havingValue = "true", matchIfMissing = false)
public class GwhPropertiesBuilder implements ApplicationListener<ContextRefreshedEvent> {


    private final GwhLoader<GwhProperty> gwhProperties;

    private final ConfigurableEnvironment configurableEnvironment;

    public GwhPropertiesBuilder(ConfigurableEnvironment configurableEnvironment, GwhLoader<GwhProperty> gwhProperties) {
        this.configurableEnvironment = configurableEnvironment;
        this.gwhProperties = gwhProperties;
    }

    @Override
    public void onApplicationEvent(@SuppressWarnings("null") ContextRefreshedEvent event) {

        Map<String, Object> propertySource = new HashMap<>();
//        gwhProperties.getAll().forEach((key, value) -> propertySource.put((key, value));
        gwhProperties.getAll().stream().forEach(property -> propertySource.put(property.getKey(), property.getValue()));
        log.info("Loaded configurations");
        configurableEnvironment.getPropertySources().addFirst(new MapPropertySource("database", propertySource));

    }

}
