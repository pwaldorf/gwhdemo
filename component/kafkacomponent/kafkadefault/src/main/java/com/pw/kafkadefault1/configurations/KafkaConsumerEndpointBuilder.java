package com.pw.kafkadefault1.configurations;

import org.apache.camel.builder.EndpointConsumerBuilder;

public interface KafkaConsumerEndpointBuilder {

    EndpointConsumerBuilder getConsumerEndpoint();

}
