package com.pw.gwhcore1.gwhproperties2;

import com.pw.api1.configuration.GwhPropertiesResource;
import com.pw.api1.configuration.GwhProperty;
import com.pw.gwhcore1.GwhConfigurationProperties;
import com.pw.gwhcore1.GwhDefaultResourceLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
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
        BindResult<GwhDatabaseProperties> databaseProperties = Binder.get(
                event.getApplicationContext().getEnvironment()).bind("gwh.properties.db", Bindable.of(GwhDatabaseProperties.class));
        BindResult<GwhConfigurationProperties> configurationProperties = Binder.get(
                event.getApplicationContext().getEnvironment()).bind("gwh.service", Bindable.of(GwhConfigurationProperties.class));

        GwhPropertiesResource gwhPropertiesResource = new GwhDatabasePropertiesResource(configurationProperties.get(), databaseProperties.get());
        GwhDefaultResourceLoader<GwhProperty> defaultResourceLoader = new GwhDefaultResourceLoader<>(gwhPropertiesResource);
        Map<String, Object> propertySource = new HashMap<>();
        defaultResourceLoader.getResource(configurationProperties.get())
                .forEach(property -> propertySource.put(property.getKey(), property.getValue()));
        event.getApplicationContext().getEnvironment().getPropertySources().addFirst(new MapPropertySource("gwhdatabase", propertySource));
        log.info("Loaded configurations");
    }
}