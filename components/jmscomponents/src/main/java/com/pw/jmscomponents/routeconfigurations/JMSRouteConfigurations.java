package com.pw.jmscomponents.routeconfigurations;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gtw.framework.component.jms.enabled", havingValue = "true", matchIfMissing = false)
public class JMSRouteConfigurations extends RouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {
        
        routeConfiguration("jmsError")
            .onException(Exception.class)
            .handled(true)            
            .log("JMS Error: ${exception.message}")
            .end();

    }    
}
