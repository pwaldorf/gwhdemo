package com.pw.activemq.gwhdefault.consumer.default1.routeconfiguration;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.component.activemq.consumer.default1.enabled", havingValue = "true", matchIfMissing = false)
public class ActiveMqDefaultConsumerRouteConfiguration extends RouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {

        routeConfiguration("activeMqDefault1ConsumerError")
            .onException(Exception.class)
            .handled(true)
            .log("ActiveMqDefault Error: ${exception.message}")
            .end();
    }
}
