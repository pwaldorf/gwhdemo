package com.pw.kafkadefault.producer.default1.routeconfiguration;

import org.apache.camel.builder.RouteConfigurationBuilder;

import com.pw.api1.GwhCoreProperties;

public class KafkaTestMessageConfiguration extends RouteConfigurationBuilder {

    private final GwhCoreProperties gwhCoreProperties;

    public KafkaTestMessageConfiguration(GwhCoreProperties gwhCoreProperties) {
        this.gwhCoreProperties = gwhCoreProperties;
    }

    @Override
    public void configuration() throws Exception {
        routeConfiguration("skipTestMessage").precondition(gwhCoreProperties.getFileterTestMessages())
            .description("Used to skip and log Test Messages")
            .interceptSendToEndpoint("kafka*")
            .skipSendToOriginalEndpoint()
            .when(simple("$property.skipTestMessage == 'true'"))
                .log("Skipping Test Message");  // create a bean to log message information
    }

}
