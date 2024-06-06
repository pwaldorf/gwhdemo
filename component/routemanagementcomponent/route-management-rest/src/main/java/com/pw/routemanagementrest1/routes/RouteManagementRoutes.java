package com.pw.routemanagementrest1.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.routemanagement.rest1.enabled", havingValue = "true", matchIfMissing = false)
public class RouteManagementRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        restConfiguration().inlineRoutes(true);

        templatedRoute("routeManagementRestTemplate_v1")
                .routeId("startRoute")
                .parameter("command", "start")
                .parameter("commandRouteId", "startRoute");

        templatedRoute("routeManagementRestTemplate_v1")
                .routeId("stopRoute")
                .parameter("command", "stop")
                .parameter("commandRouteId", "stopRoute");

        templatedRoute("routeManagementRestTemplate_v1")
                .routeId("addRoute")
                .parameter("command", "add")
                .parameter("commandRouteId", "addRoute");

        templatedRoute("routeManagementRestTemplate_v1")
                .routeId("listRoutes")
                .parameter("command", "list")
                .parameter("commandRouteId", "listRoutes")
                .parameter("method", "get")
                .parameter("path", "");

        templatedRoute("routeManagementCommandTemplate_v1")
                .routeId("startRouteDirect")
                .parameter("commandRoute", "startRoute")
                .parameter("commandProcessor", "startRouteProcessor");

        templatedRoute("routeManagementCommandTemplate_v1")
                .routeId("stopRouteDirect")
                .parameter("commandRoute", "stopRoute")
                .parameter("commandProcessor", "stopRouteProcessor");

        templatedRoute("routeManagementCommandTemplate_v1")
                .routeId("addRouteDirect")
                .parameter("commandRoute", "addRoute")
                .parameter("commandProcessor", "addRouteProcessor");

        templatedRoute("routeManagementCommandTemplate_v1")
                .routeId("listRouteDirect")
                .parameter("commandRoute", "listRoutes")
                .parameter("commandProcessor", "listRoutesProcessor");
    }

}
