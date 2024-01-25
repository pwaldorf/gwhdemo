package com.pw.activemqtest.routeconfigurations;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gw.framework.component.activemqtest.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqTestRouteConfigurations extends RouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {
        
        routeConfiguration("activeMqTestError")
            .onException(Exception.class)
            .handled(true)            
            .log("ActiveMqTest Error: ${exception.message}")
            .end();
    }    
}
