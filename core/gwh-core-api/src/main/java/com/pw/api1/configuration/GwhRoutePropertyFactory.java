package com.pw.api1.configuration;

public interface GwhRoutePropertyFactory {

    public GwhRouteProperty createRouteProperty(String routeId,
                                                String key,
                                                String value);
}
