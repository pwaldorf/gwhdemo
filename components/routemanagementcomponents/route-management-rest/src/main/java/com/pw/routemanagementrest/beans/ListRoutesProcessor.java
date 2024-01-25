package com.pw.routemanagementrest.beans;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.Route;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.routemanagement.rest.enabled", havingValue = "true", matchIfMissing = false)
public class ListRoutesProcessor implements Processor{

    private static final Logger LOGGER = LoggerFactory.getLogger(ListRoutesProcessor.class);

    @Override
    public void process(Exchange exchange) throws Exception {
        
        List<Route> routes = exchange.getContext().getRoutes();

        List<String> routeNames = routes.stream()
                .map(Route::getId)
                .collect(Collectors.toList());
        exchange.getIn().setBody(routeNames.toString());
        exchange.getIn().setHeader(Exchange.CONTENT_TYPE, "text/plain");        
        
        LOGGER.info("ListRoutesProcessor: routeIds: {} ", routeNames.toString());     
        
    }
    
}
