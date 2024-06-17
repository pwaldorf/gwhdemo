package com.pw.routemanagement1.beans;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.ExtendedCamelContext;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.ResourceHelper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component("addRouteProcessor")
@ConditionalOnProperty(value = "gwh.framework.core.routemanagement.enabled", havingValue = "true", matchIfMissing = false)
public class AddRouteProcessor implements Processor {

    @Override
    public void process(Exchange exchange) {

        String routeId = exchange.getIn().getHeader("routeId", String.class);
        String newRoute = exchange.getIn().getBody(String.class);

        log.debug("AddRouteProcessor: routeId: {} newRoute: {}", routeId, newRoute);

        try {
            exchange.getContext().addRoutes(new RouteBuilder() {

                @Override
                public void configure() throws Exception {
                    Resource resource = ResourceHelper.fromString("ResourceAdd.xml", newRoute);
                    // PluginHelper.getRoutesLoader(exchange.getContext().getCamelContextExtension()).loadRoutes(resource);
                    try (ExtendedCamelContext extendedCamelContext = exchange.getContext().adapt(ExtendedCamelContext.class)) {
                        extendedCamelContext.getRoutesLoader().loadRoutes(resource);
                    }
                }
            });

        } catch (Exception e) {
            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
            exchange.getMessage().setBody("Route not created");
        }
    }
}