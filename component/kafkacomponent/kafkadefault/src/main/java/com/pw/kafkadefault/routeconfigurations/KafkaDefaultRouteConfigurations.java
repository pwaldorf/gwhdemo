package com.pw.kafkadefault.routeconfigurations;

import org.apache.camel.builder.RouteConfigurationBuilder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import com.pw.kafkadefault.components.KafkaDefaultComponents;

@Component
@ConditionalOnBean(KafkaDefaultComponents.class)
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
