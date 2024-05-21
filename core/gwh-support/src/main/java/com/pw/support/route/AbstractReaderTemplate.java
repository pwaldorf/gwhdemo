package com.pw.support.route;

import java.util.List;

import org.apache.camel.builder.EndpointConsumerBuilder;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.spi.RoutePolicy;

public abstract class AbstractReaderTemplate extends EndpointRouteBuilder {

    protected final EndpointConsumerBuilder endpointConsumerBuilder;

    public AbstractReaderTemplate(EndpointConsumerBuilder endpointConsumerBuilder) {
        this.endpointConsumerBuilder = endpointConsumerBuilder;
    }

    protected RoutePolicy[] routePolicies(List<RoutePolicy> routePolicies) {
        return routePolicies.toArray(RoutePolicy[]::new);
    }
}
