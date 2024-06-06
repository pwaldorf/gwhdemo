package com.pw.routemanagementrest1.beans;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;


@Component("addTemplatedRoute")
@ConditionalOnProperty(value = "gwh.framework.component.routemanagement.rest1.enabled", havingValue = "true", matchIfMissing = false)
public class AddTemplatedRoute implements Processor{

    @Override
    public void process(Exchange exchange) throws Exception {
        
        String routeId = exchange.getIn().getHeader("routeId", String.class);
        //Route route = exchange.getContext().getRoute(routeId);
     
        try {
            exchange.getContext().addTemplatedRoutes(new RouteBuilder() {

                @Override
                public void configure() {

                    templatedRoute("kafkalogger")
                        .routeId(routeId)                                                
                        .parameter("topic", "testbox")
                        .parameter("brokers", "192.168.6.1:29093");
                    
                }});
        } catch (Exception e) {            
            exchange.getMessage().setHeader(Exchange.HTTP_RESPONSE_CODE, 404);
            exchange.getMessage().setBody("Route not created");
        }
    }
    
}
