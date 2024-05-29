package com.pw.support.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.lang.Nullable;

public abstract class AbstractProducerTemplate<T> extends EndpointRouteBuilder {

    private final T endpointProducerBuilder;

    private final DefaultRoutePolicies routePolicies;

    public AbstractProducerTemplate(T endpointProducerBuilder, @Nullable DefaultRoutePolicies routePolicies) {
        this.endpointProducerBuilder = endpointProducerBuilder;
        this.routePolicies = routePolicies;
    }

    public T getEndpointProducerBuilder() {
        return endpointProducerBuilder;
    }

    public DefaultRoutePolicies getRoutePolicies() {
        return routePolicies != null ? routePolicies : new DefaultRoutePolicy() {

        };
    }

}
