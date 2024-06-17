package com.pw.api1.configuration;

public interface GwhRouteTemplateFactory {

    public GwhRouteTemplate createRouteTemplate(String routeId,
                                                String templateParamName,
                                                String templateParamValue);
}
