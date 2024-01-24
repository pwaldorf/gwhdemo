package com.pw.gwhcore.routes;

//import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class LoggingTemplates extends RouteBuilder {

    @Override
    public void configure() throws Exception {
                        
        // from("direct:logger")
        // .routeId("logging_route")
        // .routeDescription("logging template")        
        // .log(LoggingLevel.INFO, "Headers: ${in.headers}\n")
        // .log(LoggingLevel.INFO, "Original Message Id: ${header.GWHOriginalMessageID}\n")
        // .log(LoggingLevel.INFO, "Body: ${body}\n");

        templatedRoute("message_logger")        
        .parameter("directname", "logger")        
        .routeId("message_logger");
        
    }
    
}
