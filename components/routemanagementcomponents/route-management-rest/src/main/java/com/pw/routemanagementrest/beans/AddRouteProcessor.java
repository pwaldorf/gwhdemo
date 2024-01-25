package com.pw.routemanagementrest.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spi.Resource;
import org.apache.camel.support.PluginHelper;
import org.apache.camel.support.ResourceHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component("addRouteProcessor")
@ConditionalOnProperty(value = "gwh.framework.component.routemanagement.rest.enabled", havingValue = "true", matchIfMissing = false)
public class AddRouteProcessor implements Processor {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddRouteProcessor.class);

    @Override
    public void process(Exchange exchange) {
        
        String routeId = exchange.getIn().getHeader("routeId", String.class);
        String newRoute = exchange.getIn().getBody(String.class);

        LOGGER.debug("AddRouteProcessor: routeId: " + routeId + " newRoute: " + newRoute);
     
        try {
            exchange.getContext().addRoutes(new RouteBuilder() {

                @Override
                public void configure() throws Exception {
                    Resource resource = ResourceHelper.fromString("ResourceAdd.xml", newRoute);
                    PluginHelper.getRoutesLoader(exchange.getContext().getCamelContextExtension()).loadRoutes(resource);
                }
            });
                
        } catch (Exception e) {
            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
            exchange.getMessage().setBody("Route not created");
        }
    }
}