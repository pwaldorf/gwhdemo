package com.pw.activemqdefault.configurations;

import org.apache.camel.builder.EndpointConsumerBuilder;

public interface ActiveMqConsumerEndpointBuilder {

    EndpointConsumerBuilder getConsumerEndpoint();
}
