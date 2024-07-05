package com.pw.gwhcore1.gwhproperties;

import com.pw.api1.GwhResource;
import com.pw.api1.configuration.GwhProperty;
import com.pw.gwhcore1.GwhConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.MapPropertySource;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GwhPropertiesLoader implements ApplicationListener<ApplicationContextInitializedEvent> {

    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent event) {

        BindResult<GwhConfigurationProperties> configurationProperties = Binder.get(
                event.getApplicationContext().getEnvironment()).bind("gwh.service",
                Bindable.of(GwhConfigurationProperties.class));

        GwhResource<GwhProperty> gwhResource = GwhPropertiesFactory.getPropertiesResource(event);

        if (gwhResource == null) {
            return;
        }

        Map<String, Object> propertySource = new HashMap<>();
        gwhResource.getResource(configurationProperties.get().getProfile(), configurationProperties.get().getRegion(), configurationProperties.get().getVersion())
                .forEach(property -> propertySource.put(property.getKey(), property.getValue()));
        event.getApplicationContext().getEnvironment().getPropertySources().addFirst(
                new MapPropertySource("gwhproperties", propertySource));
        log.info("Loaded configurations");

    }
}