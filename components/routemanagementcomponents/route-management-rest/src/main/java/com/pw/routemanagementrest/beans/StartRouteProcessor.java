package com.pw.routemanagementrest.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Component("startRouteProcessor")
@ConditionalOnProperty(value = "gwh.framework.component.routemanagement.rest.enabled", havingValue = "true", matchIfMissing = false)
public class StartRouteProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        
        String routeId = exchange.getIn().getHeader("routeId", String.class);
        Route route = exchange.getContext().getRoute(routeId);

        if (route != null) {            
            exchange.getContext().getRouteController().startRoute(routeId);
        } else {
            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
            exchange.getMessage().setBody("Route not found");
        }
    }
}