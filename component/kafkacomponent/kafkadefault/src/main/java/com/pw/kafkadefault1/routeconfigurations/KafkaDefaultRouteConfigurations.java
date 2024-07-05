package com.pw.kafkadefault1.routeconfigurations;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "gwh.framework.component.kafka.default1.default.enabled", havingValue = "true", matchIfMissing = false)
public class KafkaDefaultRouteConfigurations extends RouteConfigurationBuilder {

    @Override
    public void configuration() throws Exception {

        routeConfiguration("kafkaError")
            .onException(Exception.class)
            .handled(true)
            .log("Kafka Error: ${exception.message}")
            .end();

    }
}
