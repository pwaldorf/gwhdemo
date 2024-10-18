package com.pw.kafkadefault.producer.default1.routeconfiguration;

import org.apache.camel.builder.RouteConfigurationBuilder;

import com.pw.api1.GwhCoreProperties;

public class KafkaTestHeaderConfiguration extends RouteConfigurationBuilder {

    private final GwhCoreProperties gwhCoreProperties;

    public KafkaTestHeaderConfiguration(GwhCoreProperties gwhCoreProperties) {
        this.gwhCoreProperties = gwhCoreProperties;
    }

    @Override
    public void configuration() throws Exception {
        routeConfiguration("skipTestMessage").precondition(gwhCoreProperties.getFileterTestMessages())
            .description("Used to skip and log Test Messages")
            .interceptFrom("kafka*")
            // .when(method(getClass())) //add bean to determin to set header
            .setProperty("skipTestMessage", constant("true"));

    }

}
