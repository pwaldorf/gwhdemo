package com.pw.gwhcore1.gwhproperties;

import com.pw.api1.GwhProfileResource;
import com.pw.api1.configuration.GwhProperty;
import com.pw.gwhcore1.GwhConfigurationProperties;
import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;

public class GwhPropertiesFactory {

    public static GwhProfileResource<GwhProperty> getPropertiesResource(ApplicationContextInitializedEvent event) {
        final GwhConfigurationProperties gwhConfigurationProperties = Binder.get(
                event.getApplicationContext().getEnvironment()).bind("gwh.service",
                Bindable.of(GwhConfigurationProperties.class)).get();

        if (("database").equalsIgnoreCase(gwhConfigurationProperties.getPropertiesResource())) {
            return new GwhDatabasePropertiesResource(event);
        }
        return null;
    }
}
