package com.pw.routemanagementrest.routetemplates;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RouteManagementTemplates extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        
        routeTemplate("routeManagementRestTemplate")
        .description("template to create route management processors")
        .templateParameter("command")
        .description("name of processor command (start, stop, add)")        
        .templateParameter("commandRouteId")
        .description("route id of management route")
        .templateParameter("method", "post")
        .description("url method")
        .templateParameter("path", "{routeId}")
        .description("url path")
        .from("rest:{{method}}:/{{commandRouteId}}/{{path}}")
        .log(LoggingLevel.DEBUG, "{{command}} Route ${body}")
        .to("direct:{{commandRouteId}}")
        .log(LoggingLevel.DEBUG, "Route ${body} completed");

        routeTemplate("routeManagementCommandTemplate")
        .description("direct command route requests")
        .templateParameter("commandRoute")
        .description("name of processor command (start, stop, add)")        
        .templateParameter("commandProcessor")
        .description("management route command processor")
        .from("direct:{{commandRoute}}")
        .toD("bean:{{commandProcessor}}?method=process")
        .log(LoggingLevel.INFO, "Route ${body} completed");
        
    }
    
}
