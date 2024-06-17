package com.pw.gwhcore1.gwhroutetemplates;

import com.pw.api1.configuration.GwhRouteTemplate;
import com.pw.api1.configuration.GwhRouteTemplateFactory;
import org.springframework.stereotype.Component;

@Component
public class GwhDefaultRouteTemplateFactory implements GwhRouteTemplateFactory {

    @Override
    public GwhRouteTemplate createRouteTemplate(String routeId,
                                                       String templateParamName,
                                                       String templateParamValue) {
        return new GwhDefaultRouteTemplate(routeId, templateParamName, templateParamValue);
    }
}
