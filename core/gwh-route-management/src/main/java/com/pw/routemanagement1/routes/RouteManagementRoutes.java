package com.pw.routemanagement1.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.core.routemanagement.enabled", havingValue = "true", matchIfMissing = false)
public class RouteManagementRoutes extends RouteBuilder {

    @Override
    public void configure() throws Exception {

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
