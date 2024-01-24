package com.pw.kafkacomponents.routeconfigurations;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gtw.framework.component.kafka.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaRouteConfigurations extends RouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {
        
        routeConfiguration("kafkaError")
            .onException(Exception.class)
            .handled(true)
            .log("Kafka Error: ${exception.message}")
            .end();

    }    
}
