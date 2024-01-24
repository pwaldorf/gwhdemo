package com.pw.routemanagementrest.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.springframework.stereotype.Component;


@Component("stopRouteProcessor")
public class StopRouteProcessor implements Processor {
    
    @Override
    public void process(Exchange exchange) throws Exception {
        
        String routeId = exchange.getIn().getHeader("routeId", String.class);
        System.out.println("Requested Stop Route: " + routeId);
        Route route = exchange.getContext().getRoute(routeId);

        if (route != null) {
            exchange.getContext().getRouteController().stopRoute(routeId);
            System.out.println("Stop Requested for Route: " + routeId);
        } else {
            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
            exchange.getMessage().setBody("Route not found");
        }        
    }
}
