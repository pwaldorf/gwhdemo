package com.pw.kafkadefault.consumer.default1.routeconfiguration;

import org.apache.camel.builder.RouteConfigurationBuilder;

public class KafkaIncomingMessageLogger extends RouteConfigurationBuilder{

    @Override
    public void configuration() throws Exception {
        routeConfiguration("incomingMessageLogger")
            .onCompletion()
            .onCompleteOnly()
                /// write message to kafka topic for successful consumption
                .log("Received message: ${body}")
            .onFailureOnly()
                /// write message to kafka topic for failed consumption
                .log("Failed to process message: ${body}")
            .end();
    }

}
