package com.pw.gwhcore.routetemplates;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class LoggingRouteTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {
        
        routeTemplate("message_logger")
        .templateParameter("directname")        
        .from("direct:{{directname}}")        
        .routeDescription("logging template")        
        .log(LoggingLevel.INFO, "Headers: ${in.headers}\n")
        .log(LoggingLevel.INFO, "Original Message Id: ${header.GWHOriginalMessageID}\n")
        .log(LoggingLevel.INFO, "Body: ${body}\n");
    }
    
}
