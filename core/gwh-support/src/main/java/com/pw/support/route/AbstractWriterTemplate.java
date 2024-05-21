package com.pw.support.route;

import java.util.List;

import org.apache.camel.builder.EndpointProducerBuilder;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.spi.RoutePolicy;

public abstract class AbstractWriterTemplate extends EndpointRouteBuilder {

    protected final EndpointProducerBuilder endpointProducerBuilder;

    public AbstractWriterTemplate(EndpointProducerBuilder endpointProducerBuilder) {
        this.endpointProducerBuilder = endpointProducerBuilder;
    }

    protected RoutePolicy[] routePolicies(List<RoutePolicy> routePolicies) {
        return routePolicies.toArray(RoutePolicy[]::new);
    }

}
