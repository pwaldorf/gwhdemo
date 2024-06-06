package com.pw.activemqdefault1.routeconfigurations;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.activemq.default1.default.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultRouteConfigurations extends RouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {

        routeConfiguration("activeMqDefaultError")
            .onException(Exception.class)
            .handled(true)
            .log("ActiveMqDefault Error: ${exception.message}")
            .end();
    }
}
