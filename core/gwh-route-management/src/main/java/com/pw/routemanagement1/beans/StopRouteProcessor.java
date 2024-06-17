package com.pw.routemanagement1.beans;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Slf4j
@Component("stopRouteProcessor")
@ConditionalOnProperty(value = "gwh.framework.core.routemanagement.enabled", havingValue = "true", matchIfMissing = false)
public class StopRouteProcessor implements Processor {
    
    @Override
    public void process(Exchange exchange) throws Exception {
        
        String routeId = exchange.getIn().getHeader("routeId", String.class);
        log.debug("Requested Stop Route: {}", routeId);
        Route route = exchange.getContext().getRoute(routeId);

        if (route != null) {
            exchange.getContext().getRouteController().stopRoute(routeId);
            log.debug("Stop Requested for Route: {}", routeId);
        } else {
            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
            exchange.getMessage().setBody("Route not found");
        }        
    }
}
