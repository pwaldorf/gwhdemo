package com.pw.routeManagementRestTemplate.routetemplates;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class RouteManagementRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().inlineRoutes(true);

        templatedRoute("routeManagementRestTemplate")
                .routeId("startRoute")                
                .parameter("command", "start")
                .parameter("commandRouteId", "startRoute");

        templatedRoute("routeManagementRestTemplate")
                .routeId("stopRoute")                
                .parameter("command", "stop")
                .parameter("commandRouteId", "stopRoute");

        templatedRoute("routeManagementRestTemplate")
                .routeId("addRoute")                
                .parameter("command", "add")
                .parameter("commandRouteId", "addRoute");

        templatedRoute("routeManagementRestTemplate")
                .routeId("listRoutes")                
                .parameter("command", "list")
                .parameter("commandRouteId", "listRoutes")
                .parameter("method", "get")
                .parameter("path", "");
                
        templatedRoute("routeManagementCommandTemplate")
                .routeId("startRouteDirect")                
                .parameter("commandRoute", "startRoute")
                .parameter("commandProcessor", "startRouteProcessor");

        templatedRoute("routeManagementCommandTemplate")
                .routeId("stopRouteDirect")                
                .parameter("commandRoute", "stopRoute")
                .parameter("commandProcessor", "stopRouteProcessor");

        templatedRoute("routeManagementCommandTemplate")
                .routeId("addRouteDirect")                
                .parameter("commandRoute", "addRoute")
                .parameter("commandProcessor", "addRouteProcessor");

        templatedRoute("routeManagementCommandTemplate")
                .routeId("listRouteDirect")                
                .parameter("commandRoute", "listRoutes")
                .parameter("commandProcessor", "listRoutesProcessor");
    }
    
}
