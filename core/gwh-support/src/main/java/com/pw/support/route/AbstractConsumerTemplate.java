package com.pw.support.route;

import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.springframework.lang.Nullable;

public abstract class AbstractConsumerTemplate<T> extends EndpointRouteBuilder {

    private final T endpointConsumerBuilder;

    private final DefaultRoutePolicies routePolicies;

    public AbstractConsumerTemplate(T endpointConsumerBuilder, @Nullable DefaultRoutePolicies routePolicies) {
        this.endpointConsumerBuilder = endpointConsumerBuilder;
        this.routePolicies = routePolicies;
    }

    public T getEndpointConsumerBuilder() {
        return endpointConsumerBuilder;
    }

    public DefaultRoutePolicies getRoutePolicies() {
        return routePolicies != null ? routePolicies : new DefaultRoutePolicy() {

        };
    }

}
