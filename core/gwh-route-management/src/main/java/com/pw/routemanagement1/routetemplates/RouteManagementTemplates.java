package com.pw.routemanagement1.routetemplates;

import com.pw.routemanagement1.model.GwhListRoute;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.core.routemanagement.enabled", havingValue = "true", matchIfMissing = false)
public class RouteManagementTemplates extends RouteBuilder{

    @Override
    public void configure() throws Exception {

        routeTemplate("routeManagementCommandTemplate_v1").description("direct command route requests")
            .templateParameter("commandRoute").description("name of processor command (start, stop, add)")
            .templateParameter("commandProcessor").description("management route command processor")
            .from("direct:{{commandRoute}}")
            .unmarshal().json(GwhListRoute.class)
            .toD("bean:{{commandProcessor}}?method=process")
            .marshal().json()
            .log(LoggingLevel.INFO, "Route ${body} completed")
                .routeProperty("pjwtest", "pjwtestvalue")
            .routeGroup("RouteManagement_v1");

    }

}
