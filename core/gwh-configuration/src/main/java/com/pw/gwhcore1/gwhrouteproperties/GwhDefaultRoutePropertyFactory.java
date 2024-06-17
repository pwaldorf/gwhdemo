package com.pw.gwhcore1.gwhrouteproperties;

import com.pw.api1.configuration.GwhRouteProperty;
import com.pw.api1.configuration.GwhRoutePropertyFactory;
import org.springframework.stereotype.Component;

@Component
public class GwhDefaultRoutePropertyFactory implements GwhRoutePropertyFactory {

    @Override
    public GwhRouteProperty createRouteProperty(String routeId, String key, String value) {
        return new GwhDefaultRouteProperty(routeId, key, value);
    }
}
