package com.pw.routemanagementrest1.routetemplates;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.routemanagement.rest1.enabled", havingValue = "true", matchIfMissing = false)
public class RouteManagementTemplatesRest extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        routeTemplate("routeManagementRestTemplate_v1").description("template to create route management processors")
            .templateParameter("command").description("name of processor command (start, stop, add)")
            .templateParameter("commandRouteId").description("route id of management route")
            .templateParameter("method", "post").description("url method")
            .templateParameter("urlpath", "").description("url path")
            .from("rest:{{method}}:/{{commandRouteId}}/{{urlpath}}")
            .log(LoggingLevel.DEBUG, "{{command}} Route ${body}")
            .to("direct:{{commandRouteId}}")
            .log(LoggingLevel.DEBUG, "Route ${body} completed");

    }

}
