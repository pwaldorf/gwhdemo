package com.pw.kafkadefault.configurations;

import org.apache.camel.builder.EndpointProducerBuilder;

public interface KafkaProducerEndpointBuilder {

    EndpointProducerBuilder getProducerEndpoint();

}
