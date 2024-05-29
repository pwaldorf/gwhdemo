package com.pw.kafkadefault.configurations;

import org.apache.camel.builder.EndpointConsumerBuilder;

public interface KafkaConsumerEndpointBuilder {

    EndpointConsumerBuilder getConsumerEndpoint();

}
